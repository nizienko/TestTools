package TestTools.database.build;


import TestTools.database.AbstractMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by def on 30.10.14.
 */
public class BuildMapper extends AbstractMapper implements RowMapper<Build> {
    public Build mapRow(ResultSet resultSet, int i) throws SQLException {
        Build build = new Build();
        build.setId(resultSet.getInt("id"));
        build.setVersionId(resultSet.getInt("version_id"));
        build.setName(resultSet.getString("name"));
        build.setDescription(resultSet.getString("description"));
        Date givenDt = null;
        Date estimatedDt = null;
        Date finishedDt = null;
        try {
            givenDt = dateFormat.parse(resultSet.getString("given_dt"));
        } catch (Exception e) {
        }
        build.setGivenDt(givenDt);
        try {
            estimatedDt = dateFormat.parse(resultSet.getString("estimated_dt"));
        } catch (Exception e) {
        }
        build.setEstimatedDt(estimatedDt);
        try {
            finishedDt = dateFormat.parse(resultSet.getString("finished_dt"));
        } catch (Exception e) {
        }
        build.setFinishedDt(finishedDt);
        return build;
    }
}
