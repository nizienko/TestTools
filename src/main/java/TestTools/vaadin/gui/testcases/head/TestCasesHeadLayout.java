package TestTools.vaadin.gui.testcases.head;

import TestTools.Settings;
import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.database.testsuite.TestSuite;
import TestTools.vaadin.gui.MySelect;
import TestTools.vaadin.gui.testcases.body.TestCasesBodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

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

    public TestCasesHeadLayout(final TestCasesBodyLayout bodyLayout) {
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        projectSelect = new MySelect();
        this.addComponent(projectSelect);
        for (Project project : daoContainer.getProjectDao().selectAll()) {
            projectSelect.addItem(project.getName());
        }
        testSuiteSelect = new MySelect();
        this.addComponent(testSuiteSelect);

        projectSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenProject;
                try {
                    chosenProject = valueChangeEvent.getProperty().getValue().toString();
                    currentProject = daoContainer.getProjectDao().selectByName(chosenProject);
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectByProject(currentProject));
                    testSuiteSelect.removeAllItems();
                    for (TestSuite testSuite : daoContainer.getTestSuiteDao().selectByProject(currentProject)) {
                        testSuiteSelect.addItem(testSuite.getTestSuiteName());
                    }
                } catch (NullPointerException e) {
                    currentProject = null;
                    bodyLayout.updateTests(daoContainer.getTestCaseDao().selectAll());
                    testSuiteSelect.removeAllItems();
                }
            }
        });

        testSuiteSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenTestSuite;
                try {
                    chosenTestSuite = valueChangeEvent.getProperty().getValue().toString();
                    currentTestSuite = daoContainer.getTestSuiteDao().selectByProjectAndName(currentProject, chosenTestSuite);
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

    }


}
