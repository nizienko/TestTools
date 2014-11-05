package TestTools.vaadin.gui.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.vaadin.gui.body.BodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;

/**
 * Created by def on 05.11.14.
 */
public class HeadLayout extends HorizontalLayout {
    private ListSelect projectSelect;
    private ListSelect versionSelect;
    private ListSelect buildSelect;
    private DaoContainer daoContainer;

    public HeadLayout(final BodyLayout bodyLayout) {
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        projectSelect = new ListSelect("Project");
        projectSelect.setRows(1);
        projectSelect.setNullSelectionAllowed(false);
        projectSelect.setImmediate(true);
        this.addComponent(projectSelect);
        for (Project project : daoContainer.getProjectDao().selectAll()) {
            projectSelect.addItem(project.getName());
        }
        projectSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenProject = valueChangeEvent.getProperty().getValue().toString();
                System.out.println(chosenProject);
                bodyLayout.updateLatestTests(daoContainer.getFullTestExecutionDao().selectLastByProject(50, chosenProject));
            }
        });

    }

}
