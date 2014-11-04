package TestTools.database.testTable;

import TestTools.database.AbstractDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by def on 23.10.14.
 */
public class TestTableDao extends AbstractDao {

    public TestTableDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void insert(TestTable testTable) {
        String SQL = "insert into test_table1 (test, status) values (?, ?);";
        jdbcTemplate.update(SQL, testTable.getTest(), testTable.getStatus());
    }

    public List<TestTable> select() {
        String SQL = "select * from test_table1;";
        return jdbcTemplate.query(SQL, new TestTableMapper());
    }

}
