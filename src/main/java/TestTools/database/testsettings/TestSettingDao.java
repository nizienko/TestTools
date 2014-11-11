package TestTools.database.testsettings;

import TestTools.database.AbstractDao;
import TestTools.database.testcase.TestCase;
import TestTools.database.testcase.TestCaseMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * Created by def on 12.11.14.
 */
public class TestSettingDao extends AbstractDao {
    public TestSettingDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS \"testconfiguration\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"tk\" TEXT UNIQUE,\n" +
                "    \"description\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
        SQL = "CREATE TABLE IF NOT EXISTS \"parameters\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"parameter\" TEXT UNIQUE,\n" +
                "    \"description\" TEXT\n" +
                ");";
        jdbcTemplate.execute(SQL);
        SQL = "CREATE TABLE IF NOT EXISTS \"testvalues\" (\n" +
                "    \"tk_id\" INTEGER,\n" +
                "    \"parameter_id\" INTEGER,\n" +
                "    \"value\" TEXT,\n" +
                "PRIMARY KEY(tk_id, parameter_id)" +
                ");";
        jdbcTemplate.execute(SQL);
    }

    public void insertTestConfiguration(String name, String description) {
        String SQL = "insert into testconfiguration (tk, description) values (?, ?);";
        jdbcTemplate.update(SQL, name, description);
    }

    public void insertParameter(String name, String description) {
        String SQL = "insert into parameters (parameter, description) values (?, ?);";
        jdbcTemplate.update(SQL, name, description);
    }

    public void updateTestConfiguration(Integer id, String name, String description) {
        String SQL = "update testconfiguration set tk=?, description=? where id=?";
        jdbcTemplate.update(SQL,
                name,
                description,
                id
        );
    }

    public void updateParameter(Integer id, String name, String description) {
        String SQL = "update testconfiguration set tk=?, description=? where id=?";
        jdbcTemplate.update(SQL,
                name,
                description,
                id
        );
    }

    public void insertValue(String tk, String parameter, String value) {
        String SQL = "insert into parameters (tk_id, parameter_id, value) " +
                "values ((select id from testconfiguration where tk=?), " +
                "(select id from parameters where parameter=?), " +
                "?);";
        jdbcTemplate.update(SQL, tk, parameter, value);
    }

    public void updateValue(String tk, String parameter, String value) {
        String SQL = "update testvalues set value=? where " +
                "tk_id in (select id from testconfiguration where tk=?) " +
                "and parameter_id in (select id from parameters where parameter=?)";
        jdbcTemplate.update(SQL,
                value,
                tk,
                parameter
        );
    }

    public List<HashMap<String, String>> selectByTK(String tk) {
        String SQL = "select p.parameter, tv.value " +
                "from testvalues tv join parameters p " +
                "on tv.tk_id=p.id " +
                "join testconfiguration tk " +
                "on tv.tk_id=tk.id " +
                "where tk.id=?;";
        return jdbcTemplate.query(SQL, new Object[]{tk}, new TestSettingMapper());
    }

    public List<HashMap<String, String>> selectByTKContains(String tk, String param) {
        String SQL = "select p.parameter parameter, tv.value value " +
                "from testvalues tv join parameters p " +
                "on tv.tk_id=p.id " +
                "join testconfiguration tk " +
                "on tv.tk_id=tk.id " +
                "where tk.id=? " +
                "and p.parameter like '%?%';";
        return jdbcTemplate.query(SQL, new Object[]{tk, param}, new TestSettingMapper());
    }
}
