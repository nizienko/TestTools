package TestTools.database.testexecution;

import TestTools.database.AbstractDao;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testsuite.TestSuite;
import TestTools.database.version.Version;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created by def on 03.11.14.
 */
public class TestExecutionDao extends AbstractDao {
    public TestExecutionDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"testexecution\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"testcase_id\" INTEGER,\n" +
                "    \"status_id\" INTEGER,\n" +
                "    \"buildexecution_id\" INTEGER,\n" +
                "    \"execution_dt\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insert(TestExecution testExecution) {
        String SQL = "insert into testexecution (testcase_id, status_id, buildexecution_id, execution_dt) values (?, ?, ?, ?);";
        jdbcTemplate.update(SQL,
                testExecution.getTestCaseId(),
                testExecution.getStatusId(),
                testExecution.getBuildExecutionId(),
                dateFormat.format(testExecution.getExecutionDt())
        );
    }

    public void update(TestExecution testExecution) {
        String SQL = "update testexecution set testcase_id=?, status_id=?, buildexecution_id=?, execution_dt=? where id=?";
        jdbcTemplate.update(SQL,
                testExecution.getTestCaseId(),
                testExecution.getStatusId(),
                testExecution.getBuildExecutionId(),
                dateFormat.format(testExecution.getExecutionDt()),
                testExecution.getId()
        );
    }

    public TestExecution select(Integer id) {
        String SQL = "select id, testcase_id, status_id, buildexecution_id, execution_dt from testexecution where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new TestExecutionMapper());
    }

    public List<TestExecution> selectExecutions(
            Project project,
            Version version,
            Build build,
            BuildExecution buildExecution,
            TestSuite testSuite,
            Date sinceDate,
            Date toDate) {
        boolean isFirstParameter = true;
        StringBuffer SQL = new StringBuffer();
        SQL.append("select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
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
            isFirstParameter = false;
        } else {
            SQL.append(" and");
        }
        SQL.append(" te.execution_dt>='" + dateFormat.format(sinceDate) + "'");
        SQL.append(" and te.execution_dt<='" + dateFormat.format(toDate) + "'");
        SQL.append(" order by te.execution_dt desc");
        return jdbcTemplate.query(SQL.toString(), new Object[]{}, new TestExecutionWithNamesMapper());
    }

    public List<TestExecution> selectGroupedExecutions(
            Project project,
            Version version,
            Build build,
            BuildExecution buildExecution,
            TestSuite testSuite,
            Date sinceDate,
            Date toDate) {
        boolean isFirstParameter = true;
        StringBuffer SQL = new StringBuffer();
        SQL.append("select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
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
            isFirstParameter = false;
        } else {
            SQL.append(" and");
        }
        SQL.append(" te.execution_dt>='" + dateFormat.format(sinceDate) + "'");
        SQL.append(" and te.execution_dt<='" + dateFormat.format(toDate) + "'");
        SQL.append(" order by te.execution_dt desc");
        return jdbcTemplate.query(SQL.toString(), new Object[]{}, new TestExecutionWithNamesMapper());
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
