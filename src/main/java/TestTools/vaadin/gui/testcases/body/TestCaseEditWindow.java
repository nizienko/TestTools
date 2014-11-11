package TestTools.vaadin.gui.testcases.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testsuite.TestSuite;
import com.vaadin.data.util.TextFileProperty;
import com.vaadin.ui.*;
import org.springframework.jdbc.UncategorizedSQLException;

import java.util.List;

/**
 * Created by def on 11.11.14.
 */
public class TestCaseEditWindow extends Window {
    private DaoContainer daoContainer;
    private TestCase testCase;
    private TestSuite testSuite;
    private Project project;


    public TestCaseEditWindow(String issue, TestCasesTable testCasesTable) {
        super(issue);
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        testCase = daoContainer.getTestCaseDao().selectByIssue(issue);
        testSuite = daoContainer.getTestSuiteDao().select(testCase.getTestSuiteId());
        project = daoContainer.getProjectDao().select(testSuite.getProjectId());
        center();
        setWidth("400px");
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        final TextField issueTextField = new TextField("Issue");
        issueTextField.setValue(testCase.getIssue());
        content.addComponent(issueTextField);

        final TextField nameTextField = new TextField("Title");
        nameTextField.setValue(testCase.getName());
        content.addComponent(nameTextField);
        nameTextField.setWidth("400px");


        final TextArea descriptionTextArea = new TextArea("Description");
        descriptionTextArea.setValue(testCase.getDescription());
        content.addComponent(descriptionTextArea);
        descriptionTextArea.setSizeFull();
        descriptionTextArea.setHeight("300px");

        ListSelect testSuitSelect = new ListSelect();
        testSuitSelect.setRows(1);
        testSuitSelect.setNullSelectionAllowed(false);
        for (TestSuite ts : daoContainer.getTestSuiteDao().selectByProject(project)) {
            testSuitSelect.addItem(ts.getTestSuiteName());
        }

        final CheckBox active = new CheckBox("active");
        if (testCase.getStatus() == 1) {
            active.setValue(true);
        } else {
            active.setValue(false);
        }
        content.addComponent(active);

        content.addComponent(testSuitSelect);
        Button save = new Button("Save");
        content.addComponent(save);

        save.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                testCase.setName(nameTextField.getValue());
                testCase.setDescription(descriptionTextArea.getValue());
                testCase.setIssue(issueTextField.getValue());
                if (active.getValue()) {
                    testCase.setStatus(1);
                } else {
                    testCase.setStatus(0);
                }
                try {
                    daoContainer.getTestCaseDao().update(testCase);
                    close();
                } catch (UncategorizedSQLException e) {
                    Notification.show(testCase.getIssue() + " already exist");
                }
            }
        });

    }
}
