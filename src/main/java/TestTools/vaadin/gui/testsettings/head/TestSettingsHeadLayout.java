package TestTools.vaadin.gui.testsettings.head;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testsettings.TestConfiguration;
import TestTools.vaadin.gui.MySelect;
import TestTools.vaadin.gui.testsettings.body.AddNewParameterWindow;
import TestTools.vaadin.gui.testsettings.body.AddNewTestConfigurationWindow;
import TestTools.vaadin.gui.testsettings.body.EditParametersWindow;
import TestTools.vaadin.gui.testsettings.body.TestSettingsBodyLayout;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.springframework.jdbc.UncategorizedSQLException;


/**
 * Created by def on 12.11.14.
 */
public class TestSettingsHeadLayout extends HorizontalLayout {
    private DaoContainer daoContainer;
    private MySelect tkSelect;
    private TestConfiguration currentTestConfiguration;
    private TestSettingsBodyLayout testSettingsBodyLayout;
    private TextField parameterField;
    private Button updateButton;
    private Button addNewParameterButton;
    private Button editParametersButton;




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
        addNewParameterButton = new Button("New parameter");
        this.addComponent(addNewParameterButton);
        addNewParameterButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new AddNewParameterWindow());
            }
        });
        editParametersButton = new Button("Edit parameters");
        this.addComponent(editParametersButton);
        editParametersButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new EditParametersWindow());
            }
        });
        try {
        for (TestConfiguration tk : daoContainer.getTestSettingDao().selectTestConfigurations()) {
            tkSelect.addItem(tk.getName());
        }
        }
        catch (UncategorizedSQLException e) {}
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
                    }
                }
                catch (NullPointerException e){
                    testSettingsBodyLayout.clear();
                }
            }
        });
    }
}
