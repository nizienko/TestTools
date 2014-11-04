package TestTools.database.project;

import TestTools.database.AbstractDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by def on 29.10.14.
 */
public class ProjectDao extends AbstractDao {

    public ProjectDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"project\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"name\" TEXT,\n" +
                "    \"description\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insert(Project project) {
        String SQL = "insert into project (name, description) values (?, ?);";
        jdbcTemplate.update(SQL, project.getName(), project.getDescription());
    }

    public List<Project> selectAll() {
        String SQL = "select id, name, description from project;";
        return jdbcTemplate.query(SQL, new ProjectMapper());
    }

    public Project select(Integer id) {
        String SQL = "select id, name, description from project where id=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new ProjectMapper());
    }

    public Project selectByName(String name) {
        String SQL = "select id, name, description from project where name=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{name}, new ProjectMapper());
    }

    public void update(Project project) {
        String SQL = "update project set name=?, description=? where id=?;";
        jdbcTemplate.update(SQL, project.getName(), project.getDescription(), project.getId());
    }

    public void delete(Project project) {
        String SQL = "delete from project where id=?;";
        jdbcTemplate.update(SQL, project.getId());
    }
}
