package TestTools.vaadin.gui.testcases.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testcase.TestCase;
import TestTools.database.testsuite.TestSuite;
import com.vaadin.data.Property;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by def on 16.11.14.
 */
public class EditTestSuiteWindow extends Window {
    private DaoContainer daoContainer;
    private TextField name;
    private TextArea description;
    private Button saveButton;
    private Set<TestCase> testCaseSet;

    public EditTestSuiteWindow(final TestSuite testSuite) {
        super("Edit " + testSuite.getTestSuiteName());
        center();
        setWidth("800px");
        setHeight("500px");
        GridLayout parentLayout = new GridLayout(2, 1);
        parentLayout.setColumnExpandRatio(0, 1);
        parentLayout.setColumnExpandRatio(1, 3);
        parentLayout.setSizeFull();
        VerticalLayout content = new VerticalLayout();
        setContent(parentLayout);
        parentLayout.addComponent(content);
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        testCaseSet = null;
        name = new TextField("Name");
        name.setValue(testSuite.getTestSuiteName());
        content.addComponent(name);

        description = new TextArea("Description");
        description.setValue(testSuite.getDescription());
        content.addComponent(description);

        saveButton = new Button("Save");
        content.addComponent(saveButton);
        saveButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                testSuite.setTestSuiteName(name.getValue());
                testSuite.setDescription(description.getValue());
                daoContainer.getTestSuiteDao().update(testSuite);
                if (testCaseSet != null) {
                    daoContainer.getTestSuiteDao().deleteTestSuitesFromTestCase(testSuite);
                    for (TestCase tc : testCaseSet) {
                        daoContainer.getTestSuiteDao().insertTestCaseIntoTestSuite(tc, testSuite);
                    }
                }
                close();
            }
        });

        TwinColSelect selectCases = new TwinColSelect();
        parentLayout.addComponent(selectCases);
        selectCases.setSizeFull();
        HashSet<TestCase> preselected = new HashSet<TestCase>();
        ArrayList<Integer> i = new ArrayList<Integer>();
        for (TestCase tc : daoContainer.getTestCaseDao().selectByTestSuite(testSuite)) {
            i.add(tc.getId());
        }

        for (TestCase tc : daoContainer.getTestCaseDao().selectAll()) {
            if (i.contains(tc.getId())) {
                preselected.add(tc);
            }
            selectCases.addItem(tc);
        }
        selectCases.setValue(preselected);
        selectCases.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                if (event.getProperty().getValue() != null) {
                    testCaseSet = (Set) event.getProperty().getValue();
                }
            }
        });
    }
}
