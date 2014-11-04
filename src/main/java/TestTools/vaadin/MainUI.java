package TestTools.vaadin;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.project.Project;
import TestTools.database.project.ProjectDao;
import TestTools.vaadin.gui.MainLayout;
import TestTools.vaadin.monitor.MonitorLayout;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.UI;

import org.springframework.context.ApplicationContext;

/**
 * Created by def on 31.10.14.
 */
public class MainUI extends UI {

    @Override
    public void init(VaadinRequest request) {
        MainLayout mainLayout = new MainLayout();
        setContent(mainLayout);
    }
}
