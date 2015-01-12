package TestTools.vaadin.gui.testresults.body;


import TestTools.database.testexecution.TestExecution;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by nizienko on 05.11.14.
 */
public class LatestTestsLayout extends VerticalLayout {
    private Table table;

    public LatestTestsLayout() {
        this.setSizeFull();
        table = new Table();
        table.setWidth("100%");
        table.setHeight("100%");
        table.setEditable(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.addContainerProperty("Issue", String.class, null);
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Date", String.class, null);
        table.addContainerProperty("Status", String.class, null);
        this.addComponent(table);
    }

    public void updateLatestTests(List<TestExecution> testExecutions) {
        table.removeAllItems();
        int i = 1;
        for (TestExecution te : testExecutions) {
            String status = "failed";
            if (te.getStatusId() == 5) {
                status = "passed";
            }
            table.addItem(new Object[]{
                    te.getTestCaseIssue(),
                    te.getTestCaseName(),
                    te.getExecutionDt().toString(),
                    status
            }, new Integer(i));
            i++;
        }
    }
}
