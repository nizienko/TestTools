package TestTools.vaadin.gui;

import com.vaadin.ui.ListSelect;

/**
 * Created by def on 08.11.14.
 */
public class MySelect extends ListSelect {
    public MySelect() {
        this.setRows(1);
        this.setNullSelectionAllowed(true);
        this.setImmediate(true);
    }
}
