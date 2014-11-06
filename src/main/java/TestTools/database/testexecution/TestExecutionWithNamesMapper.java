package TestTools.database.testexecution;

import TestTools.database.AbstractMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by nizienko on 05.11.14.
 */
public class TestExecutionWithNamesMapper extends AbstractMapper implements RowMapper<TestExecution> {
    public TestExecution mapRow(ResultSet resultSet, int i) throws SQLException {
        TestExecution testExecution = new TestExecution();
        testExecution.setTestCaseIssue(resultSet.getString("issue"));
        testExecution.setTestCaseName(resultSet.getString("testcase"));
        testExecution.setPtojectName(resultSet.getString("project"));
        testExecution.setVersionName(resultSet.getString("version"));
        testExecution.setBuildName(resultSet.getString("build"));
        testExecution.setExecutionName(resultSet.getString("execution"));
        Date executionDt = null;
        try {
            executionDt = dateFormat.parse(resultSet.getString("dt"));
        } catch (Exception e) {
        }
        testExecution.setExecutionDt(executionDt);
        testExecution.setStatusId(resultSet.getInt("status"));
        return testExecution;
    }
}
