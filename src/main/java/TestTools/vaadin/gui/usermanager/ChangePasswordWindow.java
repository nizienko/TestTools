package TestTools.vaadin.gui.usermanager;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.user.User;
import com.vaadin.ui.*;

import java.security.NoSuchAlgorithmException;

/**
 * Created by def on 28.11.14.
 */
public class ChangePasswordWindow extends Window {
    public ChangePasswordWindow(final User user){
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        center();
        final PasswordField oldPasswordField = new PasswordField();
        final PasswordField newPasswordField1 = new PasswordField();
        final PasswordField newPasswordField2 = new PasswordField();
        Button button = new Button("Change password");
        layout.addComponent(new Label("Old password"));
        layout.addComponent(oldPasswordField);
        layout.addComponent(new Label("New password x2"));
        layout.addComponent(newPasswordField1);
        layout.addComponent(newPasswordField2);
        layout.addComponent(button);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                User someUser = new User();
                someUser.setPassword(oldPasswordField.getValue());
                if (someUser.getPasswordHash().equals(user.getPasswordHash())){
                    if (newPasswordField1.getValue().equals(newPasswordField2.getValue())){
                        if (!newPasswordField1.getValue().isEmpty()){
                            DaoContainer daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
                            user.setPassword(newPasswordField1.getValue());
                            daoContainer.getUserDao().updateUser(user);
                        }
                        else {
                            Notification.show("New password is empty",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    }
                    else {
                        Notification.show("Check new password",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }
                else {
                    Notification.show("Wrong old password",
                            Notification.Type.ERROR_MESSAGE);
                }
            }
        });


    }
}
