package TestTools.vaadin.gui.testresults.body;

import TestTools.database.testexecution.GroupedTestExecution;
import TestTools.database.testexecution.TestExecution;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by def on 05.11.14.
 */
public class TestResultsBodyLayout extends VerticalLayout {
    private LatestTestsLayout latestTestsLayout;
    private GroupedTestsLayout groupedTestsLayout;
    private Integer c = 1;

    public TestResultsBodyLayout() {
        latestTestsLayout = new LatestTestsLayout();
        groupedTestsLayout = new GroupedTestsLayout();
        this.addComponent(latestTestsLayout);
    }

    public void updateLatestTests(List<TestExecution> testExecutions) {
        if (c != 1) {
            this.removeAllComponents();
            this.addComponent(latestTestsLayout);
            c = 1;
        }
        latestTestsLayout.updateLatestTests(testExecutions);
    }

    public void updateGroupedTests(List<GroupedTestExecution> testExecutions) {
        if (c != 2) {
            this.removeAllComponents();
            this.addComponent(groupedTestsLayout);
            c = 2;
        }
        groupedTestsLayout.updateTests(testExecutions);
    }
}
