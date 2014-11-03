package TestTools.vaadin;

import TestTools.database.project.Project;
import TestTools.database.project.ProjectDao;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.UI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.annotation.WebServlet;

/**
 * Created by def on 31.10.14.
 */
public class MainUI extends UI {
    VerticalLayout view = new VerticalLayout();

    @Override
    public void init(VaadinRequest request) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
        ProjectDao projectDao = (ProjectDao) ctx.getBean("projectDao");
        for (Project project: projectDao.selectAll()) {
            view.addComponent(new Label(project.getName() + " " + project.getDescription()));
        }
        setContent(view);
    }
}
