package TestTools.vaadin.gui.testresults.body;

import TestTools.database.testexecution.GroupedTestExecution;
import TestTools.database.testexecution.TestExecution;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by def on 19.11.14.
 */
public class GroupedTestsLayout extends VerticalLayout {
    private Table table;

    public GroupedTestsLayout() {
        this.setSizeFull();
        table = new Table();
        table.setWidth("100%");
        table.setHeight("100%");
        table.setEditable(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.addContainerProperty("Issue", String.class, null);
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Passed", Integer.class, null);
        table.addContainerProperty("Failed", Integer.class, null);
        this.addComponent(table);
    }

    public void updateTests(List<GroupedTestExecution> groupedTestExecutions) {
        table.removeAllItems();
        int i = 1;
        for (GroupedTestExecution te : groupedTestExecutions) {
            table.addItem(new Object[]{
                    te.getIssue(),
                    te.getName(),
                    te.getPassed(),
                    te.getFailed()
            }, new Integer(i));
            i++;
        }

    }
}
