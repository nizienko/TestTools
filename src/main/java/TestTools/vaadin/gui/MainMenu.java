package TestTools.vaadin.gui;

import TestTools.core.MainApp;
import TestTools.database.user.User;
import TestTools.vaadin.gui.sqlmanager.SQLManager;
import TestTools.vaadin.gui.testcases.TestCasesLayout;
import TestTools.vaadin.gui.testresults.TestResultsLayout;
import TestTools.vaadin.gui.testsettings.TestSettingsLayout;
import TestTools.vaadin.gui.usermanager.ChangePasswordWindow;
import TestTools.vaadin.gui.usermanager.LoginWindow;
import TestTools.vaadin.gui.usermanager.UserManagerWindow;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * Created by def on 10.11.14.
 */
public class MainMenu extends MenuBar {
    private MenuItem main;
    private AppLayout appLayout;

    public MainMenu(final AppLayout appLayout) {
        this.appLayout = appLayout;
        loadMenu();
        this.focus();
        this.appLayout.changeBody(new TestResultsLayout());
    }

    public void loadMenu(){
        this.removeItems();
        MenuBar.Command testResultsCommand = new Command() {
            public void menuSelected(MenuItem menuItem) {
                appLayout.changeBody(new TestResultsLayout());
                Notification.show("Last test results", Notification.Type.TRAY_NOTIFICATION);
            }
        };
        MenuBar.Command testCasesCommand = new Command() {
            public void menuSelected(MenuItem menuItem) {
                appLayout.changeBody(new TestCasesLayout());
                Notification.show("Test case list", Notification.Type.TRAY_NOTIFICATION);
            }
        };
        MenuBar.Command settingsCommand = new Command() {
            public void menuSelected(MenuItem menuItem) {
                appLayout.changeBody(new TestSettingsLayout());
                Notification.show("Settings", Notification.Type.TRAY_NOTIFICATION);
            }
        };
        MenuBar.Command manageUsers = new Command() {
            public void menuSelected(MenuItem menuItem) {
                UI.getCurrent().addWindow(new UserManagerWindow());
                Notification.show("Users", Notification.Type.TRAY_NOTIFICATION);
            }
        };
        MenuBar.Command login = new Command() {
            public void menuSelected(MenuItem menuItem) {
                UI.getCurrent().addWindow(new LoginWindow(appLayout));
                Notification.show("Login", Notification.Type.TRAY_NOTIFICATION);
            }
        };
        MenuBar.Command stop = new Command() {
            public void menuSelected(MenuItem menuItem) {
                MainApp.stop();
                Notification.show("Stopped", Notification.Type.TRAY_NOTIFICATION);
            }
        };
        MenuBar.Command changePassword = new Command() {
            public void menuSelected(MenuItem menuItem) {
                UI.getCurrent().addWindow(new ChangePasswordWindow(appLayout.getUser()));
                Notification.show("Profile", Notification.Type.TRAY_NOTIFICATION);
            }
        };

        MenuBar.Command logout = new Command() {
            public void menuSelected(MenuItem menuItem) {
                appLayout.setUser(new User());
                Notification.show("Logout", Notification.Type.TRAY_NOTIFICATION);
            }
        };

        MenuBar.Command sqlManager = new Command() {
            public void menuSelected(MenuItem menuItem) {
                appLayout.changeBody(new SQLManager());
                Notification.show("SQL Manager", Notification.Type.TRAY_NOTIFICATION);
            }
        };

        main = this.addItem("menu", null, null);
        main.addItem("Test results", testResultsCommand);
        if (appLayout.getUser().getUserLevel() > 0) {
            MenuItem manage = main.addItem("Manage", null, null);
            manage.addItem("Settings", settingsCommand);
            if (appLayout.getUser().getUserLevel() > 6) {
                manage.addItem("Test suites", testCasesCommand);
            }
            if (appLayout.getUser().getUserLevel() > 9){
                manage.addItem("Users", manageUsers);
                manage.addItem("SQL Manager", sqlManager);
                manage.addItem("Stop application", stop);
            }
            MenuItem profile = main.addItem(appLayout.getUser().getLogin(), null, null);
            profile.addItem("Change password", changePassword);
            profile.addItem("Logout", logout);
        }
        else {
            main.addItem("Login", login);
        }
    }

}
