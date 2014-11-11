package TestTools.vaadin.gui;

import TestTools.vaadin.gui.testcases.TestCasesLayout;
import TestTools.vaadin.gui.testresults.TestResultsLayout;
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
                Notification.show("Last test results");
            }
        };
        MenuBar.Command testCasesCommand = new Command() {
            public void menuSelected(MenuItem menuItem) {
                appLayout.changeBody(new TestCasesLayout());
                Notification.show("Test cases");
            }
        };


        main = this.addItem("menu", null, null);
        main.addItem("Test results", testResultsCommand);
        main.addItem("Test cases", testCasesCommand);
        appLayout.changeBody(new TestResultsLayout());
    }
}
