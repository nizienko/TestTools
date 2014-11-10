package TestTools.vaadin.gui.testcases;

import TestTools.vaadin.gui.testcases.body.TestCasesBodyLayout;
import TestTools.vaadin.gui.testcases.head.TestCasesHeadLayout;
import com.vaadin.ui.GridLayout;

/**
 * Created by def on 11.11.14.
 */
public class TestCasesLayout extends GridLayout {
    private TestCasesHeadLayout headLayout;
    private TestCasesBodyLayout bodyLayout;

    public TestCasesLayout() {
        super(1, 2);
        this.setRowExpandRatio(1, 1);
        bodyLayout = new TestCasesBodyLayout();
        headLayout = new TestCasesHeadLayout(bodyLayout);
        this.addComponent(headLayout);
        this.addComponent(bodyLayout);
        this.setSizeFull();
        bodyLayout.setSizeFull();
    }
}
