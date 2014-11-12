package TestTools.database.testsettings;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nizienko on 12.11.14.
 */
public class TestConfigurationMapper implements RowMapper<TestConfiguration> {
    public TestConfiguration mapRow(ResultSet resultSet, int i) throws SQLException {
        TestConfiguration testConfiguration = new TestConfiguration();
        testConfiguration.setId(resultSet.getInt("id"));
        testConfiguration.setName(resultSet.getString("tk"));
        testConfiguration.setDescription("description");
        return testConfiguration;
    }
}
