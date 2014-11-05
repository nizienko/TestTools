package TestTools.database.testexecution;

import TestTools.database.AbstractDao;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by nizienko on 05.11.14.
 */
public class FullTestExecutionDao extends AbstractDao {
    public FullTestExecutionDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<FullTestExecution> selectLast(int i) {
        String SQL = "select tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id\n" +
                "order by te.execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{i}, new FullTestExecutionMapper());
    }

    public List<FullTestExecution> selectLastByProject(int i, String project) {
        String SQL = "select tc.name testcase, p.name project, v.name version, b.name build, be.name execution, te.execution_dt dt, te.status_id status \n" +
                "from testexecution te join buildexecution be on te.buildexecution_id=be.id join build b on be.build_id=b.id join version v on b.version_id=v.id join project p on v.project_id=p.id join testcase tc on te.testcase_id=tc.id\n" +
                "where p.name=?\n" +
                "order by te.execution_dt desc limit ?;";
        return jdbcTemplate.query(SQL, new Object[]{project, i}, new FullTestExecutionMapper());
    }

}
