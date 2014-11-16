package TestTools.vaadin.gui.testcases.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testsuite.TestSuite;
import com.vaadin.ui.*;
import org.springframework.jdbc.UncategorizedSQLException;

import java.util.List;

/**
 * Created by def on 11.11.14.
 */
public class TestCaseEditWindow extends Window {
    private DaoContainer daoContainer;
    private TestCase testCase;
    private List<TestSuite> testSuites;

    public TestCaseEditWindow(String issue) {
        super(issue);
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        testCase = daoContainer.getTestCaseDao().selectByIssue(issue);
        testSuites = daoContainer.getTestSuiteDao().selectByTestCase(testCase);
        center();
        setWidth("500px");
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        final TextField issueTextField = new TextField("Issue");
        issueTextField.setValue(testCase.getIssue());
        content.addComponent(issueTextField);

        final TextField nameTextField = new TextField("Title");
        nameTextField.setValue(testCase.getName());
        content.addComponent(nameTextField);
        nameTextField.setWidth("500px");


        final TextArea descriptionTextArea = new TextArea("Description");
        descriptionTextArea.setValue(testCase.getDescription());
        content.addComponent(descriptionTextArea);
        descriptionTextArea.setSizeFull();
        descriptionTextArea.setHeight("200px");


        final CheckBox active = new CheckBox("active");
        if (testCase.getStatus() == 1) {
            active.setValue(true);
        } else {
            active.setValue(false);
        }
        content.addComponent(active);

        Button save = new Button("Save");
        content.addComponent(save);
        for (TestSuite testSuite : testSuites) {
            Project project = daoContainer.getProjectDao().select(testSuite.getProjectId());
            content.addComponent(new Label(project.getName() + " " + testSuite.getTestSuiteName()));
        }
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
                    Notification.show(testCase.getIssue() + " saved.\nUpdate to view changes.", Notification.Type.TRAY_NOTIFICATION);
                } catch (UncategorizedSQLException e) {
                    Notification.show(testCase.getIssue() + " already exist");
                }
            }
        });


    }
}
