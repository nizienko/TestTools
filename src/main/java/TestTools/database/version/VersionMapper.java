package TestTools.database.version;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by def on 30.10.14.
 */
public class VersionMapper implements RowMapper<Version> {

    public Version mapRow(ResultSet resultSet, int i) throws SQLException {
        Version version = new Version();
        version.setId(resultSet.getInt("id"));
        version.setProjectId(resultSet.getInt("project_id"));
        version.setName(resultSet.getString("name"));
        version.setDescription(resultSet.getString("description"));
        return version;
    }
}
