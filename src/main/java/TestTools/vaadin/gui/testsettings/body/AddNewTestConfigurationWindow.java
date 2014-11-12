package TestTools.vaadin.gui.testsettings.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import com.vaadin.ui.*;

/**
 * Created by def on 12.11.14.
 */
public class AddNewTestConfigurationWindow extends Window {
    DaoContainer daoContainer;

    public AddNewTestConfigurationWindow() {
        super("New TK");
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        center();
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        final TextField name = new TextField("Name");
        final TextField description = new TextField("Description");
        Button save = new Button("Save");
        content.addComponent(name);
        content.addComponent(description);
        content.addComponent(save);
        save.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    if (!"".equals(name.getValue())) {
                        daoContainer.getTestSettingDao().insertTestConfiguration(name.getValue(), description.getValue());
                        close();
                        Notification.show(name.getValue() + " added",
                                Notification.Type.TRAY_NOTIFICATION);
                    } else {
                        Notification.show("Type test stand name",
                                Notification.Type.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Notification.show("Error", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

    }
}
