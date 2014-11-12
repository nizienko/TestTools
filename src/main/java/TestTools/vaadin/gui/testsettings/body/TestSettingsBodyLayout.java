package TestTools.vaadin.gui.testsettings.body;

import TestTools.database.testsettings.TestConfiguration;
import TestTools.database.testsettings.TestSetting;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by nizienko on 12.11.14.
 */
public class TestSettingsBodyLayout extends VerticalLayout {
    TestSettingsTable testSettingsTable;

    public TestSettingsBodyLayout(){
        testSettingsTable = new TestSettingsTable();
        this.addComponent(testSettingsTable);
    }
    public void updateTestSettings(TestConfiguration testConfiguration, List<TestSetting> testSettingList){
        testSettingsTable.updateTestSettings(testConfiguration, testSettingList);
    }

    public void clear(){
        testSettingsTable.clear();
    }
}
