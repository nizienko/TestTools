package TestTools.vaadin.gui.testresults.body;

import TestTools.database.testexecution.GroupedTestExecution;
import TestTools.database.testexecution.TestExecution;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by def on 19.11.14.
 */
public class GroupedTestsLayout extends VerticalLayout {
    private Table table;
    public static final Logger LOG = Logger.getLogger(GroupedTestsLayout.class);

    public GroupedTestsLayout() {
        this.setSizeFull();
        table = new Table();
        table.setWidth("100%");
        table.setHeight("100%");
        table.setEditable(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.addContainerProperty("", Integer.class, null);
        table.addContainerProperty("Issue", String.class, null);
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Passed", Integer.class, null);
        table.addContainerProperty("Failed", Integer.class, null);
        this.addComponent(table);
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                if(propertyId != null ) {
                    Item item = source.getItem(itemId);
                    Integer passed = (Integer) item.getItemProperty("Passed").getValue();
                    Integer failed = (Integer) item.getItemProperty("Failed").getValue();
                    if ((passed > 0) & failed == 0 ){
                        return "passed";
                    }
                    if ((passed > 0) & (failed > 0 )){
                        return "passed_sometimes";
                    }
                    if ((passed == 0) & (failed > 0)){
                        return "failed";
                    }
                    if ((passed == 0) & (failed == 0)){
                        return "not_run";
                    }
                    return null;
                }
                else {
                    return null;
                }
            }
        });

    }

    public void updateTests(List<GroupedTestExecution> groupedTestExecutions) {
        table.removeAllItems();
        int i = 1;
        for (GroupedTestExecution te : groupedTestExecutions) {
            table.addItem(new Object[]{
                    i,
                    te.getIssue(),
                    te.getName(),
                    te.getPassed(),
                    te.getFailed()
            }, new Integer(i));
            i++;
        }
        groupedTestExecutions.clear();

    }
}
