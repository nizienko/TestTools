package TestTools.vaadin.gui.testcases.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.database.testsuite.TestSuite;
import com.vaadin.ui.*;
import org.springframework.jdbc.UncategorizedSQLException;

/**
 * Created by def on 15.11.14.
 */
public class NewTestSuiteWindow extends Window {
    DaoContainer daoContainer;
    Project project;

    public NewTestSuiteWindow(final Project project, final ListSelect select) {
        super("New test suite to " + project.getName());
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        this.project = project;
        center();
        setWidth("400px");
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        final TextField nameTextField = new TextField("Title");
        content.addComponent(nameTextField);
        nameTextField.setWidth("400px");


        final TextArea descriptionTextArea = new TextArea("Description");
        content.addComponent(descriptionTextArea);
        descriptionTextArea.setSizeFull();
        descriptionTextArea.setHeight("200px");

        Button save = new Button("Save");
        content.addComponent(save);

        save.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                TestSuite testSuite = new TestSuite();
                if (!"".equals(nameTextField.getValue())) {
                    testSuite.setTestSuiteName(nameTextField.getValue());
                    testSuite.setDescription(descriptionTextArea.getValue());
                    testSuite.setProjectId(project.getId());
                    try {
                        daoContainer.getTestSuiteDao().insert(testSuite);
                        select.addItem(testSuite.getTestSuiteName());
                        close();
                        Notification.show(testSuite.getTestSuiteName() + " saved.", Notification.Type.TRAY_NOTIFICATION);
                    } catch (UncategorizedSQLException e) {
                        Notification.show("Already exist", Notification.Type.ERROR_MESSAGE);
                    } catch (Exception e) {
                        Notification.show("Error", Notification.Type.ERROR_MESSAGE);
                    }
                } else {
                    Notification.show("Type test suite name", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
    }

}
