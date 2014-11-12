package TestTools.vaadin.gui.testsettings.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testsettings.TestConfiguration;
import TestTools.vaadin.gui.MySelect;
import TestTools.vaadin.gui.testsettings.body.AddNewTestConfigurationWindow;
import TestTools.vaadin.gui.testsettings.body.TestSettingsBodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.*;


/**
 * Created by def on 12.11.14.
 */
public class TestSettingsHeadLayout extends HorizontalLayout {
    private DaoContainer daoContainer;
    private MySelect tkSelect;
    private TestConfiguration currentTestConfiguration;
    private TestSettingsBodyLayout testSettingsBodyLayout;
    private Label title;
    private TextField parameterField;
    private Button updateButton;


    public TestSettingsHeadLayout(final TestSettingsBodyLayout testSettingsBodyLayout) {
        this.testSettingsBodyLayout = testSettingsBodyLayout;
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        tkSelect = new MySelect();
        tkSelect.setNullSelectionAllowed(true);
        this.addComponent(tkSelect);
        parameterField = new TextField();
        this.addComponent(parameterField);
        updateButton = new Button("Update");
        this.addComponent(updateButton);
        updateButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (currentTestConfiguration != null) {
                    if ("".equals(parameterField.getValue())) {
                        testSettingsBodyLayout.updateTestSettings(currentTestConfiguration, daoContainer.getTestSettingDao().selectByTestConfiguration(currentTestConfiguration));
                    } else {
                        testSettingsBodyLayout.updateTestSettings(currentTestConfiguration, daoContainer.getTestSettingDao().selectByTestConfigurationContains(currentTestConfiguration, parameterField.getValue()));
                    }
                } else {
                    testSettingsBodyLayout.clear();
                }
            }
        });
        title = new Label(" Settings");
        this.addComponent(title);
        for (TestConfiguration tk : daoContainer.getTestSettingDao().selectTestConfigurations()) {
            tkSelect.addItem(tk.getName());
        }
        tkSelect.addItem("add new");
        tkSelect.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String chosenTK;
                try {
                    chosenTK = valueChangeEvent.getProperty().getValue().toString();
                    if ("add new".equals(chosenTK)) {
                        UI.getCurrent().addWindow(new AddNewTestConfigurationWindow());
                    } else {
                        currentTestConfiguration = daoContainer.getTestSettingDao().selectTestConfigurationByName(chosenTK);
                        if ("".equals(parameterField.getValue())) {
                            testSettingsBodyLayout.updateTestSettings(currentTestConfiguration, daoContainer.getTestSettingDao().selectByTestConfiguration(currentTestConfiguration));
                        } else {
                            testSettingsBodyLayout.updateTestSettings(currentTestConfiguration, daoContainer.getTestSettingDao().selectByTestConfigurationContains(currentTestConfiguration, parameterField.getValue()));
                        }
                        title.setValue(currentTestConfiguration.getName() + " " + currentTestConfiguration.getDescription());
                    }
                }
                catch (NullPointerException e){
                    testSettingsBodyLayout.clear();
                }
            }
        });
    }
}
