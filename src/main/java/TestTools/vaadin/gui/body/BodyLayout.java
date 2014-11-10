package TestTools.vaadin.gui.body;

import TestTools.database.testexecution.TestExecution;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by def on 05.11.14.
 */
public class BodyLayout extends VerticalLayout {
    private LatestTestsLayout latestTestsLayout;

    public BodyLayout() {
        latestTestsLayout = new LatestTestsLayout();
        this.addComponent(latestTestsLayout);
    }

    public void updateLatestTests(List<TestExecution> testExecutions) {
        latestTestsLayout.updateLatestTests(testExecutions);
    }
}
