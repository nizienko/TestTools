package TestTools.vaadin.gui.testsettings.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testsettings.TestConfiguration;
import TestTools.vaadin.gui.MySelect;
import TestTools.vaadin.gui.testsettings.body.TestSettingsBodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by def on 12.11.14.
 */
public class TestSettingsHeadLayout extends HorizontalLayout {
    private DaoContainer daoContainer;
    private MySelect tkSelect;
    private TestConfiguration currentTestConfiguration;
    private TestSettingsBodyLayout testSettingsBodyLayout;

    public TestSettingsHeadLayout(final TestSettingsBodyLayout testSettingsBodyLayout) {
        this.testSettingsBodyLayout = testSettingsBodyLayout;
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        tkSelect = new MySelect();
        tkSelect.setNullSelectionAllowed(true);
        this.addComponent(tkSelect);
        for (TestConfiguration tk : daoContainer.getTestSettingDao().selectTestConfigurations()) {
            tkSelect.addItem(tk.getName());
        }
        tkSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenTK;
                try {
                    chosenTK = valueChangeEvent.getProperty().getValue().toString();
                    currentTestConfiguration = daoContainer.getTestSettingDao().selectTestConfigurationByName(chosenTK);
                    testSettingsBodyLayout.updateTestSettings(currentTestConfiguration, daoContainer.getTestSettingDao().selectByTestConfiguration(currentTestConfiguration));
                }
                catch (NullPointerException e){
                    testSettingsBodyLayout.clear();
                }
            }
        });
    }
}
