package TestTools.vaadin.gui.testresults.head;

import TestTools.Settings;
import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.version.Version;
import TestTools.vaadin.gui.MySelect;
import TestTools.vaadin.gui.testresults.body.TestResultsBodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
/**
 * Created by def on 05.11.14.
 */
public class TestResultsHeadLayout extends HorizontalLayout {
    private MySelect projectSelect;
    private MySelect versionSelect;
    private MySelect buildSelect;
    private MySelect executionSelect;
    private DaoContainer daoContainer;
    private Project currentProject;
    private Version currentVersion;
    private Build currentBuild;
    private BuildExecution currentBuildExecution;
    private Button updateButton;

    public TestResultsHeadLayout(final TestResultsBodyLayout bodyLayout) {
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        projectSelect = new MySelect();
        this.addComponent(projectSelect);
        for (Project project : daoContainer.getProjectDao().selectAll()) {
            projectSelect.addItem(project.getName());
        }

        versionSelect = new MySelect();
        this.addComponent(versionSelect);

        buildSelect = new MySelect();
        this.addComponent(buildSelect);

        executionSelect = new MySelect();
        this.addComponent(executionSelect);

        projectSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenProject;
                try {
                    chosenProject = valueChangeEvent.getProperty().getValue().toString();
                    currentProject = daoContainer.getProjectDao().selectByName(chosenProject);
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(Settings.COUNT_TESTS_RESULTS, currentProject));
                    versionSelect.removeAllItems();
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                    for (Version version : daoContainer.getVersionDao().selectByProject(currentProject)) {
                        versionSelect.addItem(version.getName());
                    }
                } catch (NullPointerException e) {
                    currentProject = null;
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(Settings.COUNT_TESTS_RESULTS));
                    versionSelect.removeAllItems();
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                }
            }
        });
        versionSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenVersion;
                try {
                    chosenVersion = valueChangeEvent.getProperty().getValue().toString();
                    currentVersion = daoContainer.getVersionDao().selectByProjectAndName(currentProject, chosenVersion);
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByVersion(Settings.COUNT_TESTS_RESULTS, currentVersion));
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                    for (Build build : daoContainer.getBuildDao().selectByVersion(currentVersion)) {
                        buildSelect.addItem(build.getName());
                    }
                } catch (NullPointerException e) {
                    currentVersion = null;
                    if (currentProject != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(Settings.COUNT_TESTS_RESULTS, currentProject));
                    } else {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(Settings.COUNT_TESTS_RESULTS));
                    }
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                }
            }
        });
        buildSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenBuild;
                try {
                    chosenBuild = valueChangeEvent.getProperty().getValue().toString();
                    currentBuild = daoContainer.getBuildDao().selectByVersionAndName(currentVersion, chosenBuild);
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuild(Settings.COUNT_TESTS_RESULTS, currentBuild));
                    executionSelect.removeAllItems();
                    for (BuildExecution buildExecution : daoContainer.getBuildExecutionDao().selectByBuild(currentBuild)) {
                        executionSelect.addItem(buildExecution.getName());
                    }
                } catch (NullPointerException e) {
                    currentBuild = null;
                    if (currentVersion != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByVersion(Settings.COUNT_TESTS_RESULTS, currentVersion));
                    } else if (currentProject != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(Settings.COUNT_TESTS_RESULTS, currentProject));
                    } else {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(Settings.COUNT_TESTS_RESULTS));
                    }
                    executionSelect.removeAllItems();
                }
            }
        });
        executionSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenExecution;
                try {
                    chosenExecution = valueChangeEvent.getProperty().getValue().toString();
                    currentBuildExecution = daoContainer.getBuildExecutionDao().selectByBuildAndName(currentBuild, chosenExecution);
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuildExecution(Settings.COUNT_TESTS_RESULTS, currentBuildExecution));
                } catch (NullPointerException e) {
                    currentBuildExecution = null;
                    if (currentBuild != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuild(Settings.COUNT_TESTS_RESULTS, currentBuild));
                    } else if (currentVersion != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByVersion(Settings.COUNT_TESTS_RESULTS, currentVersion));
                    } else if (currentProject != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(Settings.COUNT_TESTS_RESULTS, currentProject));
                    } else {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(Settings.COUNT_TESTS_RESULTS));
                    }
                }
            }
        });

        updateButton = new Button("update");

        updateButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
/*                projectSelect.removeAllItems();
                for (Project project : daoContainer.getProjectDao().selectAll()) {
                    projectSelect.addItem(project.getName());
                }
                if (currentProject != null) {
                    projectSelect.setCaption(currentProject.getName());
                    versionSelect.removeAllItems();
                    for (Version version : daoContainer.getVersionDao().selectByProject(currentProject)) {
                        versionSelect.addItem(version.getName());
                    }
                }
                if (currentVersion != null) {
                    versionSelect.setCaption(currentVersion.getName());
                    buildSelect.removeAllItems();
                    for (Build build : daoContainer.getBuildDao().selectByVersion(currentVersion)) {
                        buildSelect.addItem(build.getName());
                    }
                }
                if (currentBuild != null) {
                    buildSelect.setCaption(currentBuild.getName());
                    executionSelect.removeAllItems();
                    for (BuildExecution buildExecution : daoContainer.getBuildExecutionDao().selectByBuild(currentBuild)) {
                        executionSelect.addItem(buildExecution.getName());
                    }
                }*/
                if (currentBuildExecution != null) {
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuildExecution(50, currentBuildExecution));
                } else if (currentBuild != null) {
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuild(50, currentBuild));
                } else if (currentVersion != null) {
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByVersion(50, currentVersion));
                } else if (currentProject != null) {
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(50, currentProject));
                } else {
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(50));
                }
            }
        });
        this.addComponent(updateButton);
    }
}
