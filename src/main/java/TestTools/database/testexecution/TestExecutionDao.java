package TestTools.database.testexecution;

import TestTools.database.AbstractDao;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.version.Version;
import org.springframework.jdbc.core.JdbcTemplate;

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

    public List<TestExecution> selectByBuildExecution(BuildExecution buildExecution) {
        String SQL = "select id, testcase_id, status_id, buildexecution_id, execution_dt from testexecution where buildexecution_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{buildExecution.getId()}, new TestExecutionMapper());
    }

    public List<TestExecution> selectLast(Integer cnt) {
        String SQL = "select id, testcase_id, status_id, buildexecution_id, execution_dt from testexecution order by execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{cnt}, new TestExecutionMapper());
    }

    public void delete(TestExecution testExecution) {
        String SQL = "delete from testexecution where id=?";
        jdbcTemplate.update(SQL, testExecution.getId());
    }

    public List<TestExecution> selectLastWithDescription(int i) {
        String SQL = "select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id\n" +
                "order by te.execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{i}, new TestExecutionWithNamesMapper());
    }

    public List<TestExecution> selectLastWithDescriptionByProject(int i, Project project) {
        String SQL = "select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id\n" +
                "where p.id=?\n" +
                "order by te.execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{project.getId(), i}, new TestExecutionWithNamesMapper());
    }

    public List<TestExecution> selectLastWithDescriptionByVersion(int i, Version version) {
        String SQL = "select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id\n" +
                "where v.id=?\n" +
                "order by te.execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{version.getId(), i}, new TestExecutionWithNamesMapper());
    }

    public List<TestExecution> selectLastWithDescriptionByBuild(int i, Build build) {
        String SQL = "select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id\n" +
                "where b.id=?\n" +
                "order by te.execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{build.getId(), i}, new TestExecutionWithNamesMapper());
    }

    public List<TestExecution> selectLastWithDescriptionByBuildExecution(int i, BuildExecution buildExecution) {
        String SQL = "select tc.issue issue, tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id\n" +
                "where be.id=?\n" +
                "order by te.execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{buildExecution.getId(), i}, new TestExecutionWithNamesMapper());
    }
}
