package TestTools.vaadin.gui;

import TestTools.vaadin.gui.testcases.TestCasesLayout;
import TestTools.vaadin.gui.testresults.TestResultsLayout;
import TestTools.vaadin.gui.testsettings.TestSettingsLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;

/**
 * Created by def on 10.11.14.
 */
public class MainMenu extends MenuBar {
    private MenuItem main;

    public MainMenu(final AppLayout appLayout) {
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


        main = this.addItem("menu", null, null);
        main.addItem("Last results", testResultsCommand);
        main.addItem("Test case list", testCasesCommand);
        main.addItem("Settings", settingsCommand);
        appLayout.changeBody(new TestResultsLayout());
    }
}
