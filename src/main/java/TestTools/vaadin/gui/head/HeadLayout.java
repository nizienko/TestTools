package TestTools.vaadin.gui.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.version.Version;
import TestTools.vaadin.gui.body.BodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by def on 05.11.14.
 */
public class HeadLayout extends HorizontalLayout {
    private HeadSelect projectSelect;
    private HeadSelect versionSelect;
    private HeadSelect buildSelect;
    private HeadSelect executionSelect;
    private DaoContainer daoContainer;
    private Project currentProject;
    private Version currentVersion;
    private Build currentBuild;
    private BuildExecution currentBuildExecution;
    private Button updateButton;

    public HeadLayout(final BodyLayout bodyLayout) {
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        projectSelect = new HeadSelect("Project");
        this.addComponent(projectSelect);
        for (Project project : daoContainer.getProjectDao().selectAll()) {
            projectSelect.addItem(project.getName());
        }

        versionSelect = new HeadSelect("Version");
        this.addComponent(versionSelect);

        buildSelect = new HeadSelect("Build");
        this.addComponent(buildSelect);

        executionSelect = new HeadSelect("Execution");
        this.addComponent(executionSelect);

        projectSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenProject;
                try {
                    chosenProject = valueChangeEvent.getProperty().getValue().toString();
                    currentProject = daoContainer.getProjectDao().selectByName(chosenProject);
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(50, currentProject));
                    versionSelect.removeAllItems();
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                    for (Version version : daoContainer.getVersionDao().selectByProject(currentProject)) {
                        versionSelect.addItem(version.getName());
                    }
                } catch (NullPointerException e) {
                    currentProject = null;
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(50));
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
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByVersion(50, currentVersion));
                    buildSelect.removeAllItems();
                    executionSelect.removeAllItems();
                    for (Build build : daoContainer.getBuildDao().selectByVersion(currentVersion)) {
                        buildSelect.addItem(build.getName());
                    }
                } catch (NullPointerException e) {
                    currentVersion = null;
                    if (currentProject != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(50, currentProject));
                    } else {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(50));
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
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuild(50, currentBuild));
                    executionSelect.removeAllItems();
                    for (BuildExecution buildExecution : daoContainer.getBuildExecutionDao().selectByBuild(currentBuild)) {
                        executionSelect.addItem(buildExecution.getName());
                    }
                } catch (NullPointerException e) {
                    currentBuild = null;
                    if (currentVersion != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByVersion(50, currentVersion));
                    } else if (currentProject != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(50, currentProject));
                    } else {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(50));
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
                    bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuildExecution(50, currentBuildExecution));
                } catch (NullPointerException e) {
                    currentBuildExecution = null;
                    if (currentBuild != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByBuild(50, currentBuild));
                    } else if (currentVersion != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByVersion(50, currentVersion));
                    } else if (currentProject != null) {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(50, currentProject));
                    } else {
                        bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(50));
                    }
                }
            }
        });

        updateButton = new Button("update");

        updateButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
               /* projectSelect.removeAllItems();
                for (Project project : daoContainer.getProjectDao().selectAll()) {
                    projectSelect.addItem(project.getName());
                }
                if (currentProject != null) {
                    versionSelect.removeAllItems();
                    for (Version version : daoContainer.getVersionDao().selectByProject(currentProject)) {
                        versionSelect.addItem(version.getName());
                    }
                }
                if (currentVersion != null) {
                    buildSelect.removeAllItems();
                    for (Build build : daoContainer.getBuildDao().selectByVersion(currentVersion)) {
                        buildSelect.addItem(build.getName());
                    }
                }
                if (currentBuild != null) {
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
