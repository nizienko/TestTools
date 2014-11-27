package TestTools.vaadin.gui.usermanager;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.user.User;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import java.awt.*;

/**
 * Created by def on 27.11.14.
 */
public class UserManagerWindow extends Window {
    private DaoContainer daoContainer;
    private final String width = "700px";


    public UserManagerWindow(){
        super("Users");
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        center();
        setWidth(width);
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        layout.setSizeUndefined();
        layout.addComponent(new NewUserPanel());
        for (User user : daoContainer.getUserDao().getUsers()){
            layout.addComponent(new UserPanel(user));
        }
    }

    private class UserPanel extends HorizontalLayout{
        private TextField loginLabel;
        private TextField level;
        private CheckBox resetPassword;
        private Button save;
        private Button delete;
        public UserPanel(final User user){
            loginLabel = new TextField();
            loginLabel.setValue(user.getLogin());
            loginLabel.setReadOnly(true);
            level = new TextField();
            level.setValue(user.getUserLevel().toString());
            resetPassword = new CheckBox("reset");
            resetPassword.setValue(false);
            save = new Button("Save");
            delete = new Button("Del");
            this.addComponent(loginLabel);
            this.addComponent(level);
            this.addComponent(resetPassword);
            this.addComponent(save);
            this.addComponent(delete);

            save.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        user.setUserLevel(Integer.parseInt(level.getValue()));
                        if (resetPassword.getValue()){
                            user.setPassword("qwerty");
                        }
                        daoContainer.getUserDao().updateUser(user);
                        Notification.show(user.getLogin() + " saved",
                                Notification.Type.TRAY_NOTIFICATION);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Notification.show("Error",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }
            });
            delete.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    daoContainer.getUserDao().delete(user);
                    save.setVisible(false);
                    loginLabel.setReadOnly(false);
                    loginLabel.setValue("deleted");
                    loginLabel.setReadOnly(true);
                    delete.setVisible(false);
                }
            });

        }
    }

    private class NewUserPanel extends HorizontalLayout{
        private TextField login;
        private TextField level;
        private TextField newPassword;
        private Button save;
        public NewUserPanel(){
            login = new TextField("login");
            level = new TextField("level");
            level.setValue("1");
            newPassword = new TextField("password");
            newPassword.setValue("qwerty");
            save = new Button("Add");
            this.addComponent(login);
            this.addComponent(level);
            this.addComponent(newPassword);
            this.addComponent(save);
            save.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        User user = new User();
                        user.setLogin(login.getValue());
                        user.setUserLevel(Integer.parseInt(level.getValue()));
                        user.setPassword(newPassword.getValue());
                        daoContainer.getUserDao().insert(user);
                        Notification.show(user.getLogin() + " created",
                                Notification.Type.TRAY_NOTIFICATION);
                    }
                    catch (IllegalStateException e){
                        Notification.show(e.getMessage(),
                                Notification.Type.ERROR_MESSAGE);
                    }
                    catch (Exception e){
                        Notification.show("Error",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }
            });

        }
    }
}
