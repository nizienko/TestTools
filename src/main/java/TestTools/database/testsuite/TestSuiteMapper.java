package TestTools.database.testsuite;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by def on 03.11.14.
 */
public class TestSuiteMapper implements RowMapper<TestSuite> {
    public TestSuite mapRow(ResultSet resultSet, int i) throws SQLException {
        TestSuite testSuite = new TestSuite();
        testSuite.setId(resultSet.getInt("id"));
        testSuite.setProjectId(resultSet.getInt("project_id"));
        testSuite.setTestSuiteName(resultSet.getString("testsuitename"));
        testSuite.setDescription(resultSet.getString("description"));
        return testSuite;
    }
}
