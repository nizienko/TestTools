package TestTools.database;

import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;

/**
 * Created by def on 29.10.14.
 */
public abstract class AbstractDao {
    protected JdbcTemplate jdbcTemplate;
    // dateformat for sqlite date column
    protected SimpleDateFormat dateFormat;

    public AbstractDao(){
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
