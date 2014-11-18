package TestTools.database.testexecution;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by def on 19.11.14.
 */
public class GroupedTestExecutionMapper implements RowMapper<GroupedTestExecution> {
    public GroupedTestExecution mapRow(ResultSet resultSet, int i) throws SQLException {
        GroupedTestExecution groupedTestExecution = new GroupedTestExecution();
        groupedTestExecution.setIssue(resultSet.getString("issue"));
        groupedTestExecution.setName(resultSet.getString("name"));
        groupedTestExecution.setPassed(resultSet.getInt("passed"));
        groupedTestExecution.setFailed(resultSet.getInt("failed"));
        return groupedTestExecution;
    }
}
