package TestTools.database.buildexecution;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by def on 04.11.14.
 */
public class BuildExecutionMapper implements RowMapper<BuildExecution> {
    public BuildExecution mapRow(ResultSet resultSet, int i) throws SQLException {
        BuildExecution buildExecution = new BuildExecution();
        buildExecution.setId(resultSet.getInt("id"));
        buildExecution.setBuildId(resultSet.getInt("build_id"));
        buildExecution.setName(resultSet.getString("name"));
        return buildExecution;
    }
}
