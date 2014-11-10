package TestTools.vaadin.gui.testresults;

import TestTools.vaadin.gui.testresults.body.TestResultsBodyLayout;
import TestTools.vaadin.gui.testresults.head.TestResultsHeadLayout;
import com.vaadin.ui.GridLayout;

/**
 * Created by def on 04.11.14.
 */
public class TestResultsLayout extends GridLayout {
    private TestResultsHeadLayout headLayout;
    private TestResultsBodyLayout bodyLayout;

    public TestResultsLayout() {
        super(1,2);
        this.setRowExpandRatio(1, 1);
        bodyLayout = new TestResultsBodyLayout();
        headLayout = new TestResultsHeadLayout(bodyLayout);
        this.addComponent(headLayout);
        this.addComponent(bodyLayout);
        this.setSizeFull();
        bodyLayout.setSizeFull();
    }
}
