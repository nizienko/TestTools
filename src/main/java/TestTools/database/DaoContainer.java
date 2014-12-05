package TestTools.database;

import TestTools.database.build.BuildDao;
import TestTools.database.buildexecution.BuildExecutionDao;
import TestTools.database.project.ProjectDao;
import TestTools.database.systemsettings.SystemSettingsDao;
import TestTools.database.testcase.TestCaseDao;
import TestTools.database.testexecution.TestExecutionDao;
import TestTools.database.testsettings.TestSettingDao;
import TestTools.database.testsuite.TestSuiteDao;
import TestTools.database.user.UserDao;
import TestTools.database.version.VersionDao;
import TestTools.vaadin.gui.usermanager.UserManagerWindow;
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
    private TestSuiteDao testSuiteDao;
    private VersionDao versionDao;
    private TestSettingDao testSettingDao;
    private UserDao userDao;
    private SystemSettingsDao systemSettingsDao;
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        buildDao = new BuildDao(jdbcTemplate);
        buildExecutionDao = new BuildExecutionDao(jdbcTemplate);
        projectDao = new ProjectDao(jdbcTemplate);
        testCaseDao = new TestCaseDao(jdbcTemplate);
        testExecutionDao = new TestExecutionDao(jdbcTemplate);
        testSuiteDao = new TestSuiteDao(jdbcTemplate);
        versionDao = new VersionDao(jdbcTemplate);
        testSettingDao = new TestSettingDao(jdbcTemplate);
        userDao = new UserDao(jdbcTemplate);
        systemSettingsDao = new SystemSettingsDao(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
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

    public TestSettingDao getTestSettingDao() {
        return testSettingDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public JdbcTemplate getJdbcTemplate(){return jdbcTemplate;}

    public SystemSettingsDao getSystemSettingsDao() {
        return systemSettingsDao;
    }
}
