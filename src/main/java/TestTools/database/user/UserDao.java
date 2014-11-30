package TestTools.database.user;

import TestTools.database.AbstractDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by def on 27.11.14.
 */
public class UserDao extends AbstractDao {
    public UserDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"user\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"login\" TEXT NOT NULL UNIQUE,\n" +
                "    \"password\" TEXT NOT NULL,\n" +
                "    \"level\" INTEGER NOT NULL\n" +
                ");";
        if (jdbcTemplate.queryForObject("select count(*) from user where level>9", Integer.class) == 0) {
            jdbcTemplate.execute(SQL);
            User root = new User();
            root.setLogin("root");
            root.setPassword("root");
            root.setUserLevel(10);
            insert(root);
        }
    }

    public void insert(User user) {
        String SQL = "insert into user (login, password, level) values (?, ?, ?)";
        jdbcTemplate.update(SQL,
                user.getLogin(), user.getPasswordHash(), user.getUserLevel());
    }

    public void delete(User user) {
        String SQL = "delete from user where id=?";
        jdbcTemplate.update(SQL, user.getUserId());
    }

    public User getUserByLogin(String login){
        String SQL = "select id, login, password, level from user where login=?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{login}, new UserMapper());
    }

    public void updateUser(User user){
        String SQL = "update user set password=?, level=? where id=?";
        jdbcTemplate.update(SQL, user.getPasswordHash(), user.getUserLevel(), user.getUserId());
    }
    public List<User> getUsers(){
        String SQL = "select id, login, password, level from user";
        return jdbcTemplate.query(SQL, new UserMapper());
    }
}
