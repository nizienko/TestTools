package TestTools.database;

import TestTools.database.build.BuildDao;
import TestTools.database.buildexecution.BuildExecutionDao;
import TestTools.database.project.ProjectDao;
import TestTools.database.testcase.TestCaseDao;
import TestTools.database.testexecution.FullTestExecution;
import TestTools.database.testexecution.FullTestExecutionDao;
import TestTools.database.testexecution.TestExecutionDao;
import TestTools.database.testsuite.TestSuiteDao;
import TestTools.database.version.VersionDao;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by def on 04.11.14.
 */
public class DaoContainer {
    private BuildDao buildDao;
    private BuildExecutionDao buildExecutionDao;
    private ProjectDao projectDao;
    private TestCaseDao testCaseDao;
    private TestExecutionDao testExecutionDao;
    private FullTestExecutionDao fullTestExecutionDao;
    private TestSuiteDao testSuiteDao;
    private VersionDao versionDao;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        buildDao = new BuildDao(jdbcTemplate);
        buildExecutionDao = new BuildExecutionDao(jdbcTemplate);
        projectDao = new ProjectDao(jdbcTemplate);
        testCaseDao = new TestCaseDao(jdbcTemplate);
        testExecutionDao = new TestExecutionDao(jdbcTemplate);
        fullTestExecutionDao = new FullTestExecutionDao(jdbcTemplate);
        testSuiteDao = new TestSuiteDao(jdbcTemplate);
        versionDao = new VersionDao(jdbcTemplate);
    }

    public BuildDao getBuildDao() {
        return buildDao;
    }

    public BuildExecutionDao getBuildExecutionDao() {
        return buildExecutionDao;
    }

    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public TestCaseDao getTestCaseDao() {
        return testCaseDao;
    }

    public TestExecutionDao getTestExecutionDao() {
        return testExecutionDao;
    }

    public TestSuiteDao getTestSuiteDao() {
        return testSuiteDao;
    }

    public VersionDao getVersionDao() {
        return versionDao;
    }

    public FullTestExecutionDao getFullTestExecutionDao() {
        return fullTestExecutionDao;
    }
}
