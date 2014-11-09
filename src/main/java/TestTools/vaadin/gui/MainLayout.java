package TestTools.vaadin.gui;

import TestTools.vaadin.gui.body.BodyLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import TestTools.vaadin.gui.head.HeadLayout;

/**
 * Created by def on 04.11.14.
 */
public class MainLayout extends VerticalLayout {
    private HeadLayout headLayout;
    private BodyLayout bodyLayout;

    public MainLayout() {
        bodyLayout = new BodyLayout();
        headLayout = new HeadLayout(bodyLayout);
        this.addComponent(headLayout);
        this.addComponent(bodyLayout);
    }
}
