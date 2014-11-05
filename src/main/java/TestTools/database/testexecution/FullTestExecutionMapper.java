package TestTools.database.testexecution;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nizienko on 05.11.14.
 */
public class FullTestExecutionMapper implements RowMapper<FullTestExecution> {
    public FullTestExecution mapRow(ResultSet resultSet, int i) throws SQLException {
        FullTestExecution fullTestExecution = new FullTestExecution();
        fullTestExecution.setName(resultSet.getString("testcase"));
        fullTestExecution.setPtoject(resultSet.getString("project"));
        fullTestExecution.setVersion(resultSet.getString("version"));
        fullTestExecution.setBuild(resultSet.getString("build"));
        fullTestExecution.setExecution(resultSet.getString("execution"));
        fullTestExecution.setDate(resultSet.getString("dt"));
        fullTestExecution.setStatus(resultSet.getString("status"));
        return fullTestExecution;
    }
}
