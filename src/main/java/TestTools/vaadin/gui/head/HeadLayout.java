package TestTools.vaadin.gui.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.database.version.Version;
import TestTools.vaadin.gui.body.BodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by def on 05.11.14.
 */
public class HeadLayout extends VerticalLayout {
    private HeadSelect projectSelect;
    private HeadSelect versionSelect;
    private HeadSelect buildSelect;
    private HeadSelect executionSelect;
    private DaoContainer daoContainer;

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
                String chosenProject = valueChangeEvent.getProperty().getValue().toString();
                Project project = daoContainer.getProjectDao().selectByName(chosenProject);
                bodyLayout.updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescriptionByProject(50, project));
                versionSelect.removeAllItems();
                buildSelect.removeAllItems();
                executionSelect.removeAllItems();
                for (Version version : daoContainer.getVersionDao().selectByProject(project)) {
                    versionSelect.addItem(version.getName());
                }
            }
        });
        versionSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {

            }
        });


    }

}
