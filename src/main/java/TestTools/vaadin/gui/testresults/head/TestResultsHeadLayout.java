package TestTools.vaadin.gui.testresults.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testsuite.TestSuite;
import TestTools.database.version.Version;
import TestTools.vaadin.gui.MySelect;
import TestTools.vaadin.gui.testresults.body.TestResultsBodyLayout;
import com.vaadin.data.Property;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;

import java.util.Calendar;

/**
 * Created by def on 05.11.14.
 */
public class TestResultsHeadLayout extends HorizontalLayout {
    private TestResultsBodyLayout bodyLayout;
    private MySelect projectSelect;
    private MySelect versionSelect;
    private MySelect buildSelect;
    private MySelect executionSelect;
    private MySelect testSuiteSelect;
    private DaoContainer daoContainer;
    private Project currentProject = null;
    private Version currentVersion = null;
    private Build currentBuild = null;
    private BuildExecution currentBuildExecution = null;
    private TestSuite currentTestSuite = null;
    private Button updateButton;
    private DateField sinceDate;
    private DateField toDate;
    private CheckBox grouped;
    private CheckBox failed;
    private CheckBox failedNew;


    public TestResultsHeadLayout(final TestResultsBodyLayout bodyLayout) {
        this.bodyLayout = bodyLayout;
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        projectSelect = new MySelect();
        this.addComponent(projectSelect);
        for (Project project : daoContainer.getProjectDao().selectAll()) {
            projectSelect.addItem(project);
        }
        testSuiteSelect = new MySelect();
        testSuiteSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    currentTestSuite = (TestSuite) valueChangeEvent.getProperty().getValue();
                } catch (NullPointerException e) {
                    currentTestSuite = null;
                }
                showExecutions();
            }
        });
        this.addComponent(testSuiteSelect);

        versionSelect = new MySelect();
        this.addComponent(versionSelect);

        buildSelect = new MySelect();
        this.addComponent(buildSelect);

        executionSelect = new MySelect();
        this.addComponent(executionSelect);


        projectSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    currentProject = (Project) valueChangeEvent.getProperty().getValue();
                    versionSelect.removeAllItems();
                    testSuiteSelect.removeAllItems();
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                    for (Version version : daoContainer.getVersionDao().selectByProject(currentProject)) {
                        versionSelect.addItem(version);
                    }
                    for (TestSuite testSuite : daoContainer.getTestSuiteDao().selectByProject(currentProject)) {
                        testSuiteSelect.addItem(testSuite);
                    }
                } catch (NullPointerException e) {
                    currentProject = null;
                    projectSelect.removeAllItems();
                    for (Project project : daoContainer.getProjectDao().selectAll()) {
                        projectSelect.addItem(project);
                    }
                    versionSelect.removeAllItems();
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                    testSuiteSelect.removeAllItems();
                }
                showExecutions();
            }
        });
        versionSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    currentVersion = (Version) valueChangeEvent.getProperty().getValue();
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                    for (Build build : daoContainer.getBuildDao().selectByVersion(currentVersion)) {
                        buildSelect.addItem(build);
                    }
                } catch (NullPointerException e) {
                    currentVersion = null;
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                }
                showExecutions();
            }
        });
        buildSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    currentBuild = (Build) valueChangeEvent.getProperty().getValue();
                    executionSelect.removeAllItems();
                    for (BuildExecution buildExecution : daoContainer.getBuildExecutionDao().selectByBuild(currentBuild)) {
                        executionSelect.addItem(buildExecution);
                    }
                } catch (NullPointerException e) {
                    currentBuild = null;
                    executionSelect.removeAllItems();
                }
                showExecutions();
            }
        });
        executionSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    currentBuildExecution = (BuildExecution) valueChangeEvent.getProperty().getValue();
                } catch (NullPointerException e) {
                    currentBuildExecution = null;
                }
                showExecutions();
            }
        });

        sinceDate = new DateField();
        toDate = new DateField();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        toDate.setValue(calendar.getTime());
        calendar.add(Calendar.HOUR, -5);
        sinceDate.setValue(calendar.getTime());
        sinceDate.setResolution(Resolution.MINUTE);
        toDate.setResolution(Resolution.MINUTE);
        this.addComponent(sinceDate);
        this.addComponent(toDate);
        updateButton = new Button("update");
        updateButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                showExecutions();
            }
        });
        this.addComponent(updateButton);
        grouped = new CheckBox("Group");
        grouped.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                showExecutions();
            }
        });
        this.addComponent(grouped);
        failed = new CheckBox("Failed only");
        failed.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                showExecutions();
            }
        });
        this.addComponent(failed);
        failedNew = new CheckBox("Passed -> Failed");
        failedNew.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                showExecutions();
            }
        });
        this.addComponent(failedNew);
        showExecutions();
    }

    private void showExecutions() {
        try {
            if (failedNew.getValue()) {
                bodyLayout.updateFailedNewTests(daoContainer.getTestExecutionDao().selectExecutions(
                        currentProject,
                        currentVersion,
                        currentBuild,
                        currentBuildExecution,
                        currentTestSuite,
                        sinceDate.getValue(),
                        toDate.getValue(),
                        failed.getValue()));
            }
            else if (grouped.getValue()) {
                bodyLayout.updateGroupedTests(daoContainer.getTestExecutionDao().selectGroupedExecutions(
                        currentProject,
                        currentVersion,
                        currentBuild,
                        currentBuildExecution,
                        currentTestSuite,
                        sinceDate.getValue(),
                        toDate.getValue(),
                        failed.getValue()));
            } else {
                bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectExecutions(
                        currentProject,
                        currentVersion,
                        currentBuild,
                        currentBuildExecution,
                        currentTestSuite,
                        sinceDate.getValue(),
                        toDate.getValue(),
                        failed.getValue()));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Notification.show("Incorrect data", Notification.Type.ERROR_MESSAGE);
        }
    }
}
