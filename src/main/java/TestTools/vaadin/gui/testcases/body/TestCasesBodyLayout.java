package TestTools.vaadin.gui.testcases.body;

import TestTools.database.testcase.TestCase;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by def on 11.11.14.
 */
public class TestCasesBodyLayout extends VerticalLayout {
    TestCasesTable testCasesTable;

    public TestCasesBodyLayout() {
        testCasesTable = new TestCasesTable();
        this.addComponent(testCasesTable);
    }

    public void updateTests(List<TestCase> testCases) {
        testCasesTable.updateTestCases(testCases);
    }
}
