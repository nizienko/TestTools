package TestTools.vaadin.gui.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
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
/*        table.addContainerProperty("Project", String.class, null);
        table.addContainerProperty("Version", String.class, null);
        table.addContainerProperty("Build", String.class, null);
        table.addContainerProperty("Execution", String.class, null);*/
        table.addContainerProperty("Date", String.class, null);
        table.addContainerProperty("Status", String.class, null);
        DaoContainer daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        updateLatestTests(daoContainer.getTestExecutionDao().selectLastWithDescription(50));
        this.addComponent(table);
        this.setExpandRatio(table, 1f);

    }

    public void updateLatestTests(List<TestExecution> testExecutions) {
        table.removeAllItems();
        int i = 1;
        for (TestExecution te : testExecutions) {
            String status = "failed";
            if (te.getStatusId() == 1) {
                status = "passed";
            }
            table.addItem(new Object[]{
                    te.getTestCaseIssue(),
                    te.getTestCaseName(),
                    /*te.getPtojectName(),
                    te.getVersionName(),
                    te.getBuildName(),
                    te.getExecutionName(),*/
                    te.getExecutionDt().toString(),
                    status
            }, new Integer(i));
            i++;
        }
    }
}
