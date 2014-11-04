package TestTools.database.testsuite;

import TestTools.database.AbstractDao;
import TestTools.database.project.Project;
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
                "    \"project_id\" INTEGER,\n" +
                "    \"testsuitename\" TEXT,\n" +
                "    \"description\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insert(TestSuite testSuite) {
        String SQL = "insert into testsuite (project_id, testsuitename, description) values (?, ?, ?);";
        jdbcTemplate.update(SQL, testSuite.getProjectId(), testSuite.getTestSuiteName(), testSuite.getDescription());
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

    public List<TestSuite> selectByProject(Project project) {
        String SQL = "select id, project_id, testsuitename, description from testsuite where project_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{project.getId()}, new TestSuiteMapper());
    }

    public void delete(TestSuite testSuite) {
        String SQL = "delete from testsuite where id=?";
        jdbcTemplate.update(SQL, testSuite.getId());
    }


}
