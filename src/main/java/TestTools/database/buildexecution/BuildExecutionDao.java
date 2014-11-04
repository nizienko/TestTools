package TestTools.database.buildexecution;

import TestTools.database.AbstractDao;
import TestTools.database.build.Build;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by def on 04.11.14.
 */
public class BuildExecutionDao extends AbstractDao {
    public BuildExecutionDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"buildexecution\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"build_id\" INTEGER,\n" +
                "    \"name\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insert(BuildExecution buildExecution) {
        String SQL = "insert into buildexecution (build_id, name) values (?, ?);";
        jdbcTemplate.update(SQL,
                buildExecution.getBuildId(),
                buildExecution.getName()
        );
    }

    public void update(BuildExecution buildExecution) {
        String SQL = "update buildexecution set build_id=?, name=? where id=?";
        jdbcTemplate.update(SQL,
                buildExecution.getBuildId(),
                buildExecution.getName(),
                buildExecution.getId()
        );
    }

    public BuildExecution select(Integer id) {
        String SQL = "select id, build_id, name from buildexecution where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new BuildExecutionMapper());
    }

    public List<BuildExecution> selectByBuild(Build build) {
        String SQL = "select id, build_id, name from buildexecution where build_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{build.getId()}, new BuildExecutionMapper());
    }

    public void delete(BuildExecution buildExecution) {
        String SQL = "delete from buildexecution where id=?";
        jdbcTemplate.update(SQL, buildExecution.getId());
    }

    public BuildExecution selectByBuildAndName(Build build, String execution) {
        String SQL = "select id, build_id, name from buildexecution where build_id=? and name=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{build.getId(), execution}, new BuildExecutionMapper());
    }
}
