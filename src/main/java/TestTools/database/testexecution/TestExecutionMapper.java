package TestTools.database.testexecution;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by def on 03.11.14.
 */
public class TestExecutionMapper implements RowMapper<TestExecution> {
    public TestExecution mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
