package TestTools.database.testexecution;

import TestTools.database.AbstractDao;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testsuite.TestSuite;
import TestTools.database.version.Version;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created by def on 03.11.14.
 */
public class TestExecutionDao extends AbstractDao {
    public static final Logger LOG = Logger.getLogger(TestExecutionDao.class);

    public TestExecutionDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"testexecution\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"testcase_id\" INTEGER,\n" +
                "    \"status_id\" INTEGER,\n" +
                "    \"buildexecution_id\" INTEGER,\n" +
                "    \"execution_dt\" TEXT,\n" +
                "    \"comment\" TEXT\n" +

                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insert(TestExecution testExecution) {
        String SQL = "insert into testexecution (testcase_id, status_id, buildexecution_id, execution_dt, comment) values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(SQL,
                testExecution.getTestCaseId(),
                testExecution.getStatusId(),
                testExecution.getBuildExecutionId(),
                dateFormat.format(testExecution.getExecutionDt()),
                testExecution.getComment()
        );
    }

    public void update(TestExecution testExecution) {
        String SQL = "update testexecution set testcase_id=?, status_id=?, buildexecution_id=?, execution_dt=?, comment=? where id=?";
        jdbcTemplate.update(SQL,
                testExecution.getTestCaseId(),
                testExecution.getStatusId(),
                testExecution.getBuildExecutionId(),
                dateFormat.format(testExecution.getExecutionDt()),
                testExecution.getComment(),
                testExecution.getId()
        );
    }

    public TestExecution select(Integer id) {
        String SQL = "select id, testcase_id, status_id, buildexecution_id, execution_dt, comment from testexecution where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new TestExecutionMapper());
    }

    public List<TestExecution> selectExecutions(
            Project project,
            Version version,
            Build build,
            BuildExecution buildExecution,
            TestSuite testSuite,
            Date sinceDate,
            Date toDate,
            Boolean failedOnly) {
        boolean isFirstParameter = true;
        StringBuffer SQL = new StringBuffer();
        SQL.append("select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status, te.comment comment \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id");
        if (project != null) {
            SQL.append(getParameterString("p.id", project.getId().toString(), isFirstParameter));
            isFirstParameter = false;
        }
        if (version != null) {
            SQL.append(getParameterString("v.id", version.getId().toString(), isFirstParameter));
            isFirstParameter = false;
        }
        if (build != null) {
            SQL.append(getParameterString("b.id", build.getId().toString(), isFirstParameter));
            isFirstParameter = false;
        }
        if (buildExecution != null) {
            SQL.append(getParameterString("be.id", buildExecution.getId().toString(), isFirstParameter));
            isFirstParameter = false;
        }
        if (testSuite != null) {
            SQL.append(getParameterString("tc.id in (select testcase_id from testsuitecontent where testsuite_id", testSuite.getId() + ")", isFirstParameter));
            isFirstParameter = false;
        }
        if (isFirstParameter) {
            SQL.append(" where");
        } else {
            SQL.append(" and");
        }
        SQL.append(" te.execution_dt>='" + dateFormat.format(sinceDate) + "'");
        SQL.append(" and te.execution_dt<='" + dateFormat.format(toDate) + "'");
        if (failedOnly) {
            SQL.append(" and te.status_id!=5");
        }
        SQL.append(" order by te.execution_dt desc");
        return jdbcTemplate.query(SQL.toString(), new Object[]{}, new TestExecutionWithNamesMapper());
    }

    public List<GroupedTestExecution> selectGroupedExecutions(
            Project project,
            Version version,
            Build build,
            BuildExecution buildExecution,
            TestSuite testSuite,
            Date sinceDate,
            Date toDate,
            Boolean failedOnly) {
        boolean isFirstParameter = true;
        String SQL = "select tc.issue issue, tc.name name, r.passed passed, r.failed failed from testcase tc ${JOIN} \n" +
                "(select t.testcase_id, p.passed passed, f.failed failed from \n" +
                "(select testcase_id, count(*) total from testexecution where 1=1 ${PREDICTION} group by testcase_id) t left outer join\n" +
                "(select testcase_id, count(*) passed from testexecution where status_id=5 ${PREDICTION} group by testcase_id) p on t.testcase_id=p.testcase_id left outer join\n" +
                "(select testcase_id, count(*) failed from testexecution where status_id=6 ${PREDICTION} group by testcase_id) f\n" +
                "on t.testcase_id=f.testcase_id) r\n" +
                "on tc.id=r.testcase_id";
        StringBuffer prediction = new StringBuffer("and buildexecution_id in (select be.id \n" +
                "from  buildexecution be \n" +
                "join build b on be.build_id=b.id \n" +
                "join version v on b.version_id=v.id \n" +
                "join project p on v.project_id=p.id");
        if (project != null) {
            prediction.append(getParameterString("p.id", project.getId().toString(), isFirstParameter));
            isFirstParameter = false;
        }
        if (version != null) {
            prediction.append(getParameterString("v.id", version.getId().toString(), isFirstParameter));
            isFirstParameter = false;
        }
        if (build != null) {
            prediction.append(getParameterString("b.id", build.getId().toString(), isFirstParameter));
            isFirstParameter = false;
        }
        if (buildExecution != null) {
            prediction.append(getParameterString("be.id", buildExecution.getId().toString(), isFirstParameter));
        }
        prediction.append(")");
        prediction.append(" and execution_dt>='" + dateFormat.format(sinceDate) + "'");
        prediction.append(" and execution_dt<='" + dateFormat.format(toDate) + "'");
        SQL = SQL.replace("${PREDICTION}", prediction);
        if (testSuite != null) {
            SQL = SQL.replace("${JOIN}", "left join");
            SQL = SQL + " where tc.id in (select testcase_id from testsuitecontent where testsuite_id=" + testSuite.getId() + ")";
        } else {
            SQL = SQL.replace("${JOIN}", "join");
        }
        if (failedOnly) {
            SQL = SQL + " where r.passed is null and r.failed>0";
        }
        return jdbcTemplate.query(SQL, new Object[]{}, new GroupedTestExecutionMapper());
    }

    private String getParameterString(String parameter, String value, boolean isFirst) {
        String result;
        if (isFirst) {
            result = " where " + parameter + "=" + value;
        } else {
            result = " and " + parameter + "=" + value;
        }
        return result;
    }
}
