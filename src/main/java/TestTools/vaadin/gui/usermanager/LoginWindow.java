package TestTools.vaadin.gui.usermanager;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.user.User;
import TestTools.vaadin.gui.AppLayout;
import TestTools.vaadin.gui.MainMenu;
import com.google.gwt.event.dom.client.KeyCodes;
import com.vaadin.ui.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.security.NoSuchAlgorithmException;

/**
 * Created by def on 28.11.14.
 */
public class LoginWindow extends Window {
    public LoginWindow(final AppLayout appLayout){
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        center();
        final TextField login = new TextField();
        login.focus();
        final PasswordField passwordField = new PasswordField();
        layout.addComponent(login);
        layout.addComponent(passwordField);
        Button loginButton = new Button("Login");
        layout.addComponent(loginButton);
        loginButton.setClickShortcut(KeyCodes.KEY_ENTER);
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (login.getValue().isEmpty()){
                    Notification.show("Empty username",
                            Notification.Type.ERROR_MESSAGE);
                }
                else if (passwordField.getValue().isEmpty()){
                    Notification.show("Empty password",
                            Notification.Type.ERROR_MESSAGE);
                }
                else {
                    User someUser = new User();
                    someUser.setLogin(login.getValue());
                    someUser.setPassword(passwordField.getValue());
                    try {
                        DaoContainer daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
                        User ourUser = daoContainer.getUserDao().getUserByLogin(someUser.getLogin());
                        if (ourUser.getPasswordHash().equals(someUser.getPasswordHash())){
                            appLayout.setUser(ourUser);
                            Notification.show("Welcome " + ourUser.getLogin(),
                                    Notification.Type.TRAY_NOTIFICATION);
                            close();
                        }
                        else {
                            Notification.show("Wrong password",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    }
                    catch (EmptyResultDataAccessException e){
                        Notification.show("No such username",
                                Notification.Type.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
