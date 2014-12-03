package TestTools.vaadin.gui.testcases.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.database.testsuite.TestSuite;
import TestTools.vaadin.gui.MySelect;
import TestTools.vaadin.gui.testcases.body.EditTestSuiteWindow;
import TestTools.vaadin.gui.testcases.body.NewTestSuiteWindow;
import TestTools.vaadin.gui.testcases.body.TestCasesBodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Created by def on 11.11.14.
 */
public class TestCasesHeadLayout extends HorizontalLayout {
    private DaoContainer daoContainer;
    private Project currentProject;
    private TestSuite currentTestSuite;
    private MySelect projectSelect;
    private MySelect testSuiteSelect;
    private Button updateButton;
    private Button addTestSuiteButton;
    private Button addTestCaseButton;


    public TestCasesHeadLayout(final TestCasesBodyLayout bodyLayout) {
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        projectSelect = new MySelect();
        this.addComponent(projectSelect);
        for (Project project : daoContainer.getProjectDao().selectAll()) {
            projectSelect.addItem(project);
        }
        testSuiteSelect = new MySelect();
        this.addComponent(testSuiteSelect);

        projectSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    currentProject = (Project) valueChangeEvent.getProperty().getValue();
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectByProject(currentProject));
                    testSuiteSelect.removeAllItems();
                    for (TestSuite testSuite : daoContainer.getTestSuiteDao().selectByProject(currentProject)) {
                        testSuiteSelect.addItem(testSuite);
                    }
                } catch (NullPointerException e) {
                    currentProject = null;
                    projectSelect.removeAllItems();
                    for (Project project : daoContainer.getProjectDao().selectAll()) {
                        projectSelect.addItem(project);
                    }
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectAll());
                    testSuiteSelect.removeAllItems();
                }
            }
        });

        testSuiteSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    currentTestSuite = (TestSuite) valueChangeEvent.getProperty().getValue();
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectByTestSuite(currentTestSuite));

                } catch (NullPointerException e) {
                    currentTestSuite = null;
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectByProject(currentProject));
                    testSuiteSelect.removeAllItems();
                }
            }
        });

        updateButton = new Button("update");
        this.addComponent(updateButton);
        updateButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (currentTestSuite != null) {
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectByTestSuite(currentTestSuite));
                } else if (currentProject != null) {
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectByProject(currentProject));
                } else {
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectAll());
                }
            }
        });
        updateButton.focus();
        addTestSuiteButton = new Button("Create test suite");
        this.addComponent(addTestSuiteButton);
        addTestSuiteButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (currentProject != null) {
                    UI.getCurrent().addWindow(new NewTestSuiteWindow(currentProject, testSuiteSelect));
                } else {
                    Notification.show("Select project", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        addTestCaseButton = new Button("Add test case");
        this.addComponent(addTestCaseButton);
        addTestCaseButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (currentTestSuite != null) {
                    UI.getCurrent().addWindow(new EditTestSuiteWindow(currentTestSuite));
                } else {
                    Notification.show("Select test suite", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

    }


}
