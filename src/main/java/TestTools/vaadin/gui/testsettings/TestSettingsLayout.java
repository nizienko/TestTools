package TestTools.vaadin.gui.testsettings;

import TestTools.vaadin.gui.testsettings.head.TestSettingsHead;
import com.vaadin.ui.GridLayout;

/**
 * Created by def on 12.11.14.
 */
public class TestSettingsLayout extends GridLayout {
    TestSettingsHead testSettingsHead;

    public TestSettingsLayout() {
        super(1, 2);
        this.setRowExpandRatio(1, 1);
        this.setSizeFull();
        testSettingsHead = new TestSettingsHead();

        this.addComponent(testSettingsHead);
    }
}
