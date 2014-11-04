package TestTools.database;

import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;

/**
 * Created by def on 29.10.14.
 */
public abstract class AbstractDao implements TableDao {
    protected JdbcTemplate jdbcTemplate;
    // dateformat for sqlite date column
    protected SimpleDateFormat dateFormat;

    public AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

}
