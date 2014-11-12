package TestTools.database.testsettings;

import TestTools.database.AbstractDao;
import TestTools.database.testcase.TestCase;
import TestTools.database.testcase.TestCaseMapper;
import TestTools.database.testsuite.TestSuite;
import TestTools.database.testsuite.TestSuiteMapper;
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
        String SQL = "insert into testvalues (tk_id, parameter_id, value) " +
                "values ((select id from testconfiguration where tk=?), " +
                "(select id from parameters where parameter=?), " +
                "?);";
        jdbcTemplate.update(SQL, tk, parameter, value);
    }

    public void updateValue(TestConfiguration tk, TestSetting testSetting) {
        String SQL = "update testvalues set value=? where " +
                "tk_id=? " +
                "and parameter_id in (select id from parameters where parameter=?)";
        jdbcTemplate.update(SQL,
                testSetting.getValue(),
                tk.getId(),
                testSetting.getParameterName()
        );
    }

    public List<TestSetting> selectByTestConfiguration(TestConfiguration tk) {
        String SQL = "select p.parameter parameter, tv.value value, p.description description " +
                "from testvalues tv join parameters p " +
                "on tv.tk_id=p.id " +
                "join testconfiguration tk " +
                "on tv.tk_id=tk.id " +
                "where tk.id=?";
        return jdbcTemplate.query(SQL, new Object[]{tk.getId()}, new TestSettingMapper());
    }

    public List<TestSetting> selectByTestConfigurationContains(TestConfiguration tk, String param) {
        String SQL = "select p.parameter parameter, tv.value value, p.description description " +
                "from testvalues tv join parameters p " +
                "on tv.tk_id=p.id " +
                "join testconfiguration tk " +
                "on tv.tk_id=tk.id " +
                "where tk.id=? " +
                "and p.parameter like '%?%';";
        return jdbcTemplate.query(SQL, new Object[]{tk.getId(), param}, new TestSettingMapper());
    }

    public List<TestConfiguration> selectTestConfigurations() {
        String SQL = "select id, tk, description from testconfiguration;";
        return jdbcTemplate.query(SQL, new Object[]{}, new TestConfigurationMapper());
    }
    public TestConfiguration selectTestConfigurationByName(String tk) {
        String SQL = "select id, tk, description from testconfiguration where tk=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{tk}, new TestConfigurationMapper());
    }

    public TestSetting selectTestSetting(TestConfiguration tk, String param) {
        String SQL = "select p.parameter parameter, tv.value value, p.description description " +
                "from testvalues tv join parameters p " +
                "on tv.parameter_id=p.id " +
                "where tv.tk_id=? and p.parameter=?;";
        return jdbcTemplate.queryForObject(SQL, new Object[]{tk.getId(), param}, new TestSettingMapper());
    }

}
