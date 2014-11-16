package TestTools.database.testsuite;

import TestTools.database.AbstractDao;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by def on 03.11.14.
 */
public class TestSuiteDao extends AbstractDao {
    public TestSuiteDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"testsuite\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "     \"project_id\" INTEGER NOT NULL,\n" +
                "    \"testsuitename\" TEXT NOT NULL,\n" +
                "    \"description\" TEXT,\n" +
                "    UNIQUE (\"project_id\", \"testsuitename\") \n" +
                ");";
        jdbcTemplate.execute(SQL);

        SQL = "CREATE TABLE IF NOT EXISTS \"testsuitecontent\" (\n" +
                "    \"testsuite_id\" INTEGER NOT NULL,\n" +
                "    \"testcase_id\" INTEGER NOT NULL,\n" +
                "   PRIMARY KEY(testsuite_id, testcase_id)" +
                ")\n";
        jdbcTemplate.execute(SQL);
    }

    public void insert(TestSuite testSuite) {
        String SQL = "insert into testsuite (project_id, testsuitename, description) values (?, ?, ?);";
        jdbcTemplate.update(SQL, testSuite.getProjectId(), testSuite.getTestSuiteName(), testSuite.getDescription());
    }

    public void insertTestCaseIntoTestSuite(TestCase testCase, TestSuite testSuite) {
        String SQL = "insert into testsuitecontent (testsuite_id, testcase_id) values (?, ?);";
        jdbcTemplate.update(SQL, testSuite.getId(), testCase.getId());
    }

    public void deleteTestSuitesFromTestCase(TestSuite testSuite) {
        String SQL = "delete from testsuitecontent where testsuite_id=?";
        jdbcTemplate.update(SQL, testSuite.getId());
    }

    public void update(TestSuite testSuite) {
        String SQL = "update testsuite set project_id=?, testsuitename=?, description=? where id=?";
        jdbcTemplate.update(SQL,
                testSuite.getProjectId(),
                testSuite.getTestSuiteName(),
                testSuite.getDescription(),
                testSuite.getId());
    }

    public TestSuite select(Integer id) {
        String SQL = "select id, project_id, testsuitename, description from testsuite where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new TestSuiteMapper());
    }

    public List<TestSuite> selectByTestCase(TestCase testCase) {
        String SQL = "select id, project_id, testsuitename, description from testsuite where id in (select testsuite_id from testsuitecontent where testcase_id=?);";
        return jdbcTemplate.query(SQL, new Object[]{testCase.getId()}, new TestSuiteMapper());
    }

    public List<TestSuite> selectByProject(Project project) {
        String SQL = "select id, project_id, testsuitename, description from testsuite where project_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{project.getId()}, new TestSuiteMapper());
    }

    public TestSuite selectByProjectAndName(Project project, String name) {
        String SQL = "select id, project_id, testsuitename, description from testsuite where project_id=? and testsuitename=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{project.getId(), name}, new TestSuiteMapper());
    }

    public void delete(TestSuite testSuite) {
        String SQL = "delete from testsuite where id=?";
        jdbcTemplate.update(SQL, testSuite.getId());
    }
}
