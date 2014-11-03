package TestTools.database.testexecution;

import TestTools.database.AbstractDao;
import TestTools.database.buildexecution.BuildExecution;

import java.util.List;

/**
 * Created by def on 03.11.14.
 */
public class TestExecutionDao extends AbstractDao {
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
                dateFormat.format(testExecution.getExecution_dt())
        );
    }

    public void update(TestExecution testExecution) {
        String SQL = "update testexecution set testcase_id=?, status_id=?, buildexecution_id=?, execution_dt=? where id=?";
        jdbcTemplate.update(SQL,
                testExecution.getTestCaseId(),
                testExecution.getStatusId(),
                testExecution.getBuildExecutionId(),
                dateFormat.format(testExecution.getExecution_dt()),
                testExecution.getId()
        );
    }

    public TestExecution select(Integer id) {
        String SQL = "select id, testcase_id, status_id, buildexecution_id, execution_dt from testexecution where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new TestExecutionMapper());
    }

    public List<TestExecution> selectByBuildExecution(BuildExecution buildExecution) {
        String SQL = "select id, testcase_id, status_id, c, execution_dt from testexecution where buildexecution_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{buildExecution.getId()}, new TestExecutionMapper());
    }

    public void delete(TestExecution testExecution) {
        String SQL = "delete from testexecution where id=?";
        jdbcTemplate.update(SQL, testExecution.getId());
    }
}
