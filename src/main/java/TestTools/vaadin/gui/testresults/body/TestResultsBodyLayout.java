package TestTools.vaadin.gui.testresults.body;

import TestTools.database.testexecution.TestExecution;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by def on 05.11.14.
 */
public class TestResultsBodyLayout extends VerticalLayout {
    private LatestTestsLayout latestTestsLayout;

    public TestResultsBodyLayout() {
        latestTestsLayout = new LatestTestsLayout();
        this.addComponent(latestTestsLayout);
    }

    public void updateLatestTests(List<TestExecution> testExecutions) {
        latestTestsLayout.updateLatestTests(testExecutions);
    }
}
