package TestTools.vaadin.gui.testcases.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testcase.TestCase;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by def on 11.11.14.
 */
public class TestCasesTable extends VerticalLayout {
    private Table table;

    public TestCasesTable() {
        this.setSizeFull();
        table = new Table();
        table.setWidth("100%");
        table.setHeight("100%");
        table.setEditable(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.addContainerProperty("Issue", String.class, null);
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Description", String.class, null);
        table.addContainerProperty("Status", String.class, null);
        DaoContainer daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        updateTestCases(daoContainer.getTestCaseDao().selectAll());
        this.addComponent(table);
        table.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                try {
                    String issue = (String) table.getContainerProperty(table.getValue(), "Issue").getValue();
                    UI.getCurrent().addWindow(new TestCaseEditWindow(issue));
                } catch (NullPointerException e) {
                }
            }
        });
    }

    public void updateTestCases(List<TestCase> testCases) {
        table.removeAllItems();
        int i = 1;
        for (TestCase tc : testCases) {
            String status = "off";
            if (tc.getStatus() == 1) {
                status = "on";
            }
            table.addItem(new Object[]{
                    tc.getIssue(),
                    tc.getName(),
                    tc.getDescription(),
                    status
            }, new Integer(i));
            i++;
        }
    }
}
