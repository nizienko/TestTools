package TestTools.database.build;

import TestTools.database.AbstractDao;

import java.text.SimpleDateFormat;

/**
 * Created by def on 30.10.14.
 */
public class BuildDao extends AbstractDao {

    public void createTable(){
        String SQL = "CREATE TABLE IF NOT EXISTS \"build\" (\n" +
                "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    \"version_id\" INTEGER NOT NULL,\n" +
                "    \"name\" TEXT NOT NULL,\n" +
                "    \"description\" TEXT,\n" +
                "    \"given_dt\" TEXT,\n" +
                "    \"estimated_dt\" TEXT,\n" +
                "    \"finished_dt\" TEXT\n" +
                ")";
        jdbcTemplate.execute(SQL);
    }

    public void insert(Build build){
        String SQL = "insert into build " +
                "(version_id, name, description, given_dt, estimated_dt, finished_dt) " +
                "values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL,
                build.getVersionId(),
                build.getName(),
                build.getDescription(),
                dateFormat.format(build.getFinishedDt()),
                dateFormat.format(build.getEstimatedDt()),
                dateFormat.format(build.getFinishedDt()));
    }

}
