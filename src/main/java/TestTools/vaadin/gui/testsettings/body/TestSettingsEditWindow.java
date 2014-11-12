package TestTools.vaadin.gui.testsettings.body;


import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testsettings.TestConfiguration;
import TestTools.database.testsettings.TestSetting;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by nizienko on 12.11.14.
 */
public class TestSettingsEditWindow extends Window {
    DaoContainer daoContainer;
    public TestSettingsEditWindow(TestConfiguration testConfiguration, String parameter){
        super(testConfiguration.getName() + " " + parameter);
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        TestSetting testSetting = daoContainer.getTestSettingDao().selectTestSetting(testConfiguration, parameter);
        center();
        setWidth("400px");
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        final TextField nameTextField = new TextField("Title");
        nameTextField.setValue(testSetting.getValue());
        content.addComponent(nameTextField);
        nameTextField.setWidth("400px");
    }

}
