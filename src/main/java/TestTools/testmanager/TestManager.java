package TestTools.testmanager;

import TestTools.database.DaoContainer;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.testsettings.TestConfiguration;
import TestTools.database.testsettings.TestSetting;
import TestTools.database.version.Version;
import TestTools.publisher.NotifyPublisherImpl;
import TestTools.publisher.PublisherImpl;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by def on 06.11.14.
 */
public class TestManager implements NotifyPublisherImpl{
    public static final Logger LOG = Logger.getLogger(TestManager.class);
    private boolean isAlife = true;
    private DaoContainer daoContainer;
    private ArrayList<PublisherImpl> publishers = new ArrayList<PublisherImpl>();

    public void setDaoContainer(DaoContainer daoContainer) {
        this.daoContainer = daoContainer;
    }

    public void addTestExecution(TestExecution te) throws IllegalStateException {
        try {
            Project project = null;
            try {
                project = daoContainer.getProjectDao().selectByName(te.getPtojectName());
            } catch (EmptyResultDataAccessException e) {
                project = new Project(te.getPtojectName(), "created by test issue " + te.getTestCaseIssue());
                daoContainer.getProjectDao().insert(project);
                project = daoContainer.getProjectDao().selectByName(te.getPtojectName());
                for (PublisherImpl p : publishers){
                    p.publicateProject(project);
                }
            }

            Version version = null;
            try {
                version = daoContainer.getVersionDao().selectByProjectAndName(project, te.getVersionName());
            } catch (EmptyResultDataAccessException e) {
                version = new Version(project.getId(), te.getVersionName(), "created by test issue " + te.getTestCaseIssue());
                daoContainer.getVersionDao().insert(version);
                version = daoContainer.getVersionDao().selectByProjectAndName(project, te.getVersionName());
                for (PublisherImpl p : publishers){
                    p.publicateVersion(project, version);
                }
            }

            Build build = null;
            try {
                build = daoContainer.getBuildDao().selectByVersionAndName(version, te.getBuildName());
            } catch (EmptyResultDataAccessException e) {
                build = new Build(version.getId(), te.getBuildName(), "created by test issue " + te.getTestCaseIssue(), new Date(), new Date(), new Date());
                daoContainer.getBuildDao().insert(build);
                build = daoContainer.getBuildDao().selectByVersionAndName(version, te.getBuildName());
                for (PublisherImpl p : publishers){
                    p.publicateBuild(project, version, build);
                }
            }
            BuildExecution buildExecution = null;
            try {
                buildExecution = daoContainer.getBuildExecutionDao().selectByBuildAndName(build, te.getExecutionName());
            } catch (EmptyResultDataAccessException e) {
                buildExecution = new BuildExecution(build.getId(), te.getExecutionName());
                daoContainer.getBuildExecutionDao().insert(buildExecution);
                buildExecution = daoContainer.getBuildExecutionDao().selectByBuildAndName(build, te.getExecutionName());
                for (PublisherImpl p : publishers){
                    p.publicateBuildExecution(project, version, build, buildExecution);
                }
            }
            TestCase testCase = null;
            try {
                testCase = daoContainer.getTestCaseDao().selectByIssue(te.getTestCaseIssue());
            } catch (EmptyResultDataAccessException e) {
                testCase = new TestCase();
                testCase.setIssue(te.getTestCaseIssue());
                testCase.setName(te.getTestCaseName());
                testCase.setDescription("autocreated");
                testCase.setStatus(1);
                daoContainer.getTestCaseDao().insert(testCase);
                testCase = daoContainer.getTestCaseDao().selectByIssue(te.getTestCaseIssue());
            }
            te.setTestCaseId(testCase.getId());
            te.setBuildExecutionId(buildExecution.getId());
            te.setExecutionDt(new Date());
            daoContainer.getTestExecutionDao().insert(te);
            for (PublisherImpl p : publishers){
                p.publicateTestExecution(project, version, build, buildExecution, te);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Error inserting test execution");
        }
    }

    public List<TestSetting> getSettings(String testConfigurationName, String contains) {
        TestConfiguration testConfiguration;
        try {
            testConfiguration = daoContainer.getTestSettingDao().selectTestConfigurationByName(testConfigurationName);
            List<TestSetting> testSettings;
            if (contains == null) {
                testSettings = daoContainer.getTestSettingDao().selectByTestConfiguration(testConfiguration);
            } else {
                testSettings = daoContainer.getTestSettingDao().selectByTestConfigurationContains(testConfiguration, contains);
            }
            return testSettings;
        } catch (Exception e) {
            return null;
        }
    }

    public void addPublisher(PublisherImpl publisher) {
        publishers.add(publisher);
    }

    public String getSetting(String name){
        try {
            return daoContainer.getSystemSettingsDao().getSetting(name);
        }
        catch (EmptyResultDataAccessException e) {
            throw new IllegalStateException("Can't load " + name);
        }
    }

    public boolean isAlife(){
        return isAlife;
    }

    public void stop(){
        isAlife = false;
    }

}
