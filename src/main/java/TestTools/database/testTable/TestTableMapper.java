package TestTools.database.testTable;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by def on 29.10.14.
 */
public class TestTableMapper implements RowMapper<TestTable> {
    public TestTable mapRow(ResultSet resultSet, int i) throws SQLException {
        TestTable testTable = new TestTable();
        testTable.setId(resultSet.getInt("id"));
        testTable.setTest(resultSet.getString("test"));
        testTable.setStatus(resultSet.getInt("status"));
        return testTable;
    }
}
