package TestTools.vaadin.gui.testsettings.body;


import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testsettings.TestParameter;
import TestTools.database.testsettings.TestSetting;
import TestTools.vaadin.gui.testcases.body.TestCaseEditWindow;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * Created by nizienko on 13.11.14.
 */
public class EditParametersWindow extends Window {
    DaoContainer daoContainer;
    Table table;
    Button updateButton;
    final String width = "600px";

    public EditParametersWindow(){
        super("Edit parameters");
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        center();
        setWidth(width);
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        updateButton = new Button("Update");
        content.addComponent(updateButton);
        table = new Table();
        table.setWidth("100%");
        table.setHeight("100%");
        table.setEditable(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.addContainerProperty("Parameter", String.class, null);
        table.addContainerProperty("Description", String.class, null);
        content.addComponent(table);
        this.update();
        table.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                String parameter = (String) table.getContainerProperty(table.getValue(), "Parameter").getValue();
                UI.getCurrent().addWindow(new EditParameterWindow(parameter));
                }
                catch (NullPointerException e) {}
            }
        });
        updateButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent clickEvent) {
                update();
            }
        });

    }
    private void update(){
        int i = 1;
        table.removeAllItems();
        System.out.println("Updating");
        for (TestParameter tp : daoContainer.getTestSettingDao().selectAllParameters()) {
            table.addItem(new Object[]{
                    tp.getName(),
                    tp.getDescription()
            }, new Integer(i));
            i++;
        }
    }

    private class EditParameterWindow extends Window {
        TestParameter testParameter;
        public EditParameterWindow(String parameter) {
            super(parameter);
            try {
                testParameter = daoContainer.getTestSettingDao().selectParameter(parameter);
            }
            catch (EmptyResultDataAccessException e){
                Notification.show("No such parameter, click update",
                        Notification.Type.ERROR_MESSAGE);            }
            center();
            VerticalLayout content = new VerticalLayout();
            setContent(content);
            final TextField name = new TextField("Name");
            name.setValue(testParameter.getName());
            final TextField description = new TextField("Description");
            description.setValue(testParameter.getDescription());
            Button save = new Button("Save");
            content.addComponent(name);
            content.addComponent(description);
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.addComponent(save);
            content.addComponent(horizontalLayout);
            save.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent clickEvent) {
                    try {
                        if (!"".equals(name.getValue())) {
                            testParameter.setName(name.getValue());
                            testParameter.setDescription(description.getValue());
                            daoContainer.getTestSettingDao().updateParameter(testParameter);
                            close();
                            Notification.show(name.getValue() + " updated",
                                    Notification.Type.TRAY_NOTIFICATION);
                        } else {
                            Notification.show("Type parameter name",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Notification.show("Error", Notification.Type.ERROR_MESSAGE);
                    }
                }
            });
            Button delete = new Button("Delete");
            delete.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent clickEvent) {
                    try {

                        daoContainer.getTestSettingDao().deleteParameter(testParameter);
                    Notification.show(name.getValue() + " deleted",
                            Notification.Type.TRAY_NOTIFICATION);
                        close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Notification.show("Error", Notification.Type.ERROR_MESSAGE);
                    }
                }
            });
            horizontalLayout.addComponent(delete);

        }
    }
}
