package TestTools.vaadin.gui;

import TestTools.database.testexecution.TestExecution;
import TestTools.vaadin.gui.testresults.TestResultsLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by def on 10.11.14.
 */
public class AppLayout extends GridLayout {
    private MainMenu mainMenu;
    private HorizontalLayout headLayout;
    private VerticalLayout bodyLayout;

    public AppLayout() {
        super(1, 2);
        this.setRowExpandRatio(1, 1);
        headLayout = new HorizontalLayout();
        bodyLayout = new VerticalLayout();
        mainMenu = new MainMenu(this);
        headLayout.addComponent(mainMenu);
        this.setSizeFull();
        bodyLayout.setSizeFull();
        this.addComponent(headLayout);
        this.addComponent(bodyLayout);
    }

    public void changeBody(Component component) {
        bodyLayout.removeAllComponents();
        bodyLayout.addComponent(component);
    }
}