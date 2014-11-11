package TestTools.database.testsettings;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by def on 12.11.14.
 */
public class TestSettingMapper implements RowMapper<HashMap<String, String>> {
    public HashMap<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(resultSet.getString("parameter"), resultSet.getString("value"));
        return hashMap;
    }
}
