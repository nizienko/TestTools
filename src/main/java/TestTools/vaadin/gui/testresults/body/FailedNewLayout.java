package TestTools.vaadin.gui.testresults.body;

import TestTools.database.testexecution.TestExecution;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by def on 09.02.15.
 */
public class FailedNewLayout extends VerticalLayout {
    private Table table;

    public FailedNewLayout(){
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
        table.addContainerProperty("Last", String.class, null);
        table.addContainerProperty("Previous", String.class, null);
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                if(propertyId != null ) {
                    Item item = source.getItem(itemId);
                    String status = (String) item.getItemProperty("Status").getValue();
                    if ("passed".equals(status)){
                        return "passed";
                    }
                    else if ("failed".equals(status)){
                        return "failed";
                    }
                    else {
                        return "not_run";
                    }
                }
                else {
                    return null;
                }
            }
        });
        this.addComponent(table);
    }

    public void updateLatestTests(List<TestExecution> testExecutions) {
        table.removeAllItems();
        HashMap <String, ComparedTests> result = new HashMap<>();
        for (TestExecution te : testExecutions){
            if (result.containsKey(te.getTestCaseIssue())) {
                ComparedTests currentComparedTests = result.get(te.getTestCaseIssue());
                if (te.getExecutionDt().after(currentComparedTests.getLast().getExecutionDt())) {
                    currentComparedTests.setPrevious(currentComparedTests.getLast());
                    currentComparedTests.setLast(te);
                }
                else if ((currentComparedTests.getPrevious() != null)
                        && (te.getExecutionDt().after(currentComparedTests.getPrevious().getExecutionDt()))){
                    currentComparedTests.setPrevious(te);
                }
                else if (currentComparedTests.getPrevious() == null) {
                    currentComparedTests.setPrevious(te);
                }
            }
            else {
                result.put(te.getTestCaseIssue(), new ComparedTests(te));
            }
        }

        int i = 1;

        Iterator it = result.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            ComparedTests ct = (ComparedTests) pairs.getValue();
            TestExecution last = ct.getLast();
            TestExecution previous;
            if (ct.getPrevious() != null) {
                previous = ct.getPrevious();
            }
            else {
                previous = new TestExecution();
            }
            table.addItem(new Object[]{
                    i,
                    pairs.getKey(),
                    last.getTestCaseName(),
                    last.getStatusId() + " " + last.getExecutionDt(),
                    previous.getStatusId() + " " + previous.getExecutionDt()
            }, new Integer(i));
            i++;
        }
        testExecutions.clear();
    }

    private class ComparedTests {
        private TestExecution last;
        private TestExecution previous;

        public ComparedTests(TestExecution te){
            this.last = te;
            this.previous = null;
        }

        public TestExecution getLast() {
            return last;
        }

        public void setLast(TestExecution last) {
            this.last = last;
        }

        public TestExecution getPrevious() {
            return previous;
        }

        public void setPrevious(TestExecution previous) {
            this.previous = previous;
        }
    }
}
