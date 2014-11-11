package TestTools.database.testcase;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by def on 03.11.14.
 */
public class TestCaseMapper implements RowMapper<TestCase> {
    public TestCase mapRow(ResultSet resultSet, int i) throws SQLException {
        TestCase testCase = new TestCase();
        testCase.setId(resultSet.getInt("id"));
        testCase.setTestSuiteId(resultSet.getInt("testsuite_id"));
        testCase.setIssue(resultSet.getString("issue"));
        testCase.setName(resultSet.getString("name"));
        testCase.setDescription(resultSet.getString("description"));
        testCase.setStatus(resultSet.getInt("status"));
        try {
            String[] labels = resultSet.getString("label_id").split(",");
            ArrayList<Integer> labelsList = new ArrayList<Integer>();
            for (String label : labels) {
                try {
                    labelsList.add(Integer.parseInt(label));
                } catch (NumberFormatException e) {
                }
            }
            testCase.setLabelId(labelsList);
        } catch (Exception e) {
            testCase.setLabelId(new ArrayList<Integer>());
        }
        return testCase;
    }
}
