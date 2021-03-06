package TestTools.database.testexecution;


import TestTools.database.AbstractMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by def on 03.11.14.
 */
public class TestExecutionMapper extends AbstractMapper implements RowMapper<TestExecution> {
    public TestExecution mapRow(ResultSet resultSet, int i) throws SQLException {
        TestExecution testExecution = new TestExecution();
        testExecution.setId(resultSet.getInt("id"));
        testExecution.setBuildExecutionId(resultSet.getInt("buildexecution_id"));
        Date executionDt = null;
        try {
            executionDt = dateFormat.parse(resultSet.getString("execution_dt"));
        } catch (Exception e) {
        }
        testExecution.setExecutionDt(executionDt);
        testExecution.setTestCaseId(resultSet.getInt("testcase_id"));
        testExecution.setStatusId(resultSet.getInt("status_id"));
        testExecution.setComment(resultSet.getString("comment"));
        return testExecution;
    }
}
