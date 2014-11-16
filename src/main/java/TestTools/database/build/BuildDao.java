package TestTools.database.build;

import TestTools.database.AbstractDao;
import TestTools.database.version.Version;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by def on 30.10.14.
 */
public class BuildDao extends AbstractDao {

    public BuildDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"build\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"version_id\" INTEGER NOT NULL,\n" +
                "    \"name\" TEXT NOT NULL,\n" +
                "    \"description\" TEXT,\n" +
                "    \"given_dt\" TEXT,\n" +
                "    \"estimated_dt\" TEXT,\n" +
                "    \"finished_dt\" TEXT\n" +
                ")";
        jdbcTemplate.execute(SQL);
    }

    public void insert(Build build) {
        String SQL = "insert into build " +
                "(version_id, name, description, given_dt, estimated_dt, finished_dt) " +
                "values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL,
                build.getVersionId(),
                build.getName(),
                build.getDescription(),
                dateFormat.format(build.getFinishedDt()),
                dateFormat.format(build.getEstimatedDt()),
                dateFormat.format(build.getFinishedDt()));
    }

    public List<Build> selectByVersion(Version version) {
        String SQL = "select id, version_id, name, description, given_dt, estimated_dt, finished_dt from build where version_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{version.getId()}, new BuildMapper());
    }

    public Build selectByVersionAndName(Version version, String buildName) {
        String SQL = "select id, version_id, name, description, given_dt, estimated_dt, finished_dt from build where version_id=? and name=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{version.getId(), buildName}, new BuildMapper());
    }
}
