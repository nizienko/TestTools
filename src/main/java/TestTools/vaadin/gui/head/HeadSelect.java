package TestTools.vaadin.gui.head;

import com.vaadin.ui.ListSelect;

/**
 * Created by def on 08.11.14.
 */
public class HeadSelect extends ListSelect {
    public HeadSelect(String title) {
//        super(title);
        this.setRows(1);
        this.setNullSelectionAllowed(true);
        this.setImmediate(true);
    }
}
