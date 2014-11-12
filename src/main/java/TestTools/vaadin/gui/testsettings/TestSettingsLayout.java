package TestTools.vaadin.gui.testsettings;

import TestTools.vaadin.gui.testsettings.body.TestSettingsBodyLayout;
import TestTools.vaadin.gui.testsettings.head.TestSettingsHeadLayout;
import com.vaadin.ui.GridLayout;

/**
 * Created by def on 12.11.14.
 */
public class TestSettingsLayout extends GridLayout {
    TestSettingsHeadLayout testSettingsHeadLayout;
    TestSettingsBodyLayout testSettingsBodyLayout;

    public TestSettingsLayout() {
        super(1, 2);
        this.setRowExpandRatio(1, 1);
        this.setSizeFull();
        testSettingsBodyLayout = new TestSettingsBodyLayout();
        testSettingsHeadLayout = new TestSettingsHeadLayout(testSettingsBodyLayout);
        this.addComponent(testSettingsHeadLayout);
        this.addComponent(testSettingsBodyLayout);
        this.setSizeFull();
        testSettingsBodyLayout.setSizeFull();

    }
}
