package TestTools.database.version;

import TestTools.database.AbstractDao;
import TestTools.database.project.Project;

import java.util.List;

/**
 * Created by def on 30.10.14.
 */
public class VersionDao extends AbstractDao{

    public void createTable(){
        String SQL = "CREATE TABLE IF NOT EXISTS \"version\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"project_id\" INTEGER NOT NULL,\n" +
                "    \"name\" TEXT NOT NULL,\n" +
                "    \"description\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insert(Version version){
        String SQL = "insert into version (project_id, name, description) values (?, ?, ?)";
        jdbcTemplate.update(SQL,
                version.getProjectId(),
                version.getName(),
                version.getDescription());
    }

    public void update(Version version){
        String SQL = "update version set project_id=?, name=?, description=? where id=?";
        jdbcTemplate.update(SQL,
                version.getProjectId(),
                version.getName(),
                version.getDescription(),
                version.getId());
    }

    public Version select(Integer id){
        String SQL = "select id, project_id, name, description from version where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new VersionMapper());
    }

    public List<Version> selectByProject(Project project){
        String SQL = "select id, project_id, name, description from version where project_id=?;";
        return jdbcTemplate.query(SQL, new Object[]{project.getId()}, new VersionMapper());
    }

    public void delete(Version version){
        String SQL = "delete from version where id=?";
        jdbcTemplate.update(SQL, version.getId());
    }
}
