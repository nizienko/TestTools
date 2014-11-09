package TestTools.vaadin;


import TestTools.vaadin.gui.MainLayout;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Created by def on 31.10.14.
 */
public class MainUI extends UI {

    @Override
    public void init(VaadinRequest request) {
        this.setTheme("dawn");
        MainLayout mainLayout = new MainLayout();
        this.setContent(mainLayout);
    }
}
