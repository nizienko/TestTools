package TestTools.database.systemsettings;

import TestTools.database.AbstractDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by nizienko on 05.12.14.
 */
public class SystemSettingsDao extends AbstractDao {

    public SystemSettingsDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"systemsettings\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"name\" TEXT,\n" +
                "    \"value\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
    }

    public String getSetting(String name){
        String SQL = "select value from systemsettings where name=?";
        return jdbcTemplate.queryForObject(SQL, new Object[]{name}, String.class);
    }
}
