package TestTools.database.build;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by def on 30.10.14.
 */
public class BuildMapper implements RowMapper<Build> {
    public Build mapRow(ResultSet resultSet, int i) throws SQLException {
        Build build = new Build();
        build.setId(resultSet.getInt("id"));
        build.setVersionId(resultSet.getInt("version_id"));
        build.setName(resultSet.getString("name"));
        build.setDescription(resultSet.getString("description"));
        build.setGivenDt(resultSet.getDate("given_dt"));
        build.setEstimatedDt(resultSet.getDate("estimated_dt"));
        build.setFinishedDt(resultSet.getDate("finished_dt"));
        return build;
    }
}
