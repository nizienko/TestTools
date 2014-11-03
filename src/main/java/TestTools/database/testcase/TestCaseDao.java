package TestTools.database.testcase;

import TestTools.database.AbstractDao;
import TestTools.database.testsuite.TestSuite;

import java.util.List;

/**
 * Created by def on 03.11.14.
 */
public class TestCaseDao extends AbstractDao {
    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"testcase\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"testsuite_id\" INTEGER,\n" +
                "    \"name\" TEXT,\n" +
                "    \"description\" TEXT,\n" +
                "    \"status\" integer,\n" +
                "    \"label_id\" TEXT\n" +

                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insert(TestCase testCase) {
        String SQL = "insert into testcase (testsuite_id, name, description, status, label_id) values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(SQL,
                testCase.getTestSuiteId(),
                testCase.getName(),
                testCase.getDescription(),
                testCase.getStatus(),
                testCase.getLabelId());
    }

    public void update(TestCase testCase) {
        String SQL = "update testcase set testsuite_id=?, name=?, description=?, status=?, label_id=? where id=?";
        jdbcTemplate.update(SQL,
                testCase.getTestSuiteId(),
                testCase.getName(),
                testCase.getDescription(),
                testCase.getStatus(),
                testCase.getLabelId(),
                testCase.getId()
        );
    }

    public TestCase select(Integer id) {
        String SQL = "select id, testsuite_id, name, description, status, label_id from testcase where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new TestCaseMapper());
    }

    public List<TestCase> selectByTestSuite(TestSuite testSuite) {
        String SQL = "select id, testsuite_id, name, description, status, label_id from testcase where testsuite_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{testSuite.getId()}, new TestCaseMapper());
    }

    public void delete(TestCase testCase) {
        String SQL = "delete from testcase where id=?";
        jdbcTemplate.update(SQL, testCase.getId());
    }
}
