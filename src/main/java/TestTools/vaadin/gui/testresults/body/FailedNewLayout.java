package TestTools.vaadin.gui.testresults.body;

import TestTools.database.systemsettings.SystemSettingsDao;
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
        table.addContainerProperty("Last", TestExecution.class, null);
        table.addContainerProperty("Previous", TestExecution.class, null);
        table.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                if(propertyId != null ) {
                    String col = (String)propertyId;
                    Item item = source.getItem(itemId);

                    if ("Issue".equals(col)) {
                        TestExecution last = (TestExecution) item.getItemProperty("Last").getValue();
                        TestExecution previous = (TestExecution) item.getItemProperty("Previous").getValue();

                        if ((previous == null) || (previous.getStatusId() == null)) {
                            return "not_run";
                        }
                        else if ((last.getStatusId() == 5) && (previous.getStatusId() == 6)){
                            return "passed";
                        }
                        else if ((last.getStatusId() == 5) && (previous.getStatusId() == 5)){
                            return "passed_sometimes";
                        }
                        else if ((last.getStatusId() == 6) && (previous.getStatusId() == 5)){
                            return "failed";
                        }
                        else if ((last.getStatusId() == 6) && (previous.getStatusId() == 6)){
                            return "passed_sometimes";
                        }
                        else {
                            return "not_run";
                        }
                    }
                    else if ("Last".equals(col)) {
                        TestExecution last = (TestExecution) item.getItemProperty("Last").getValue();
                        if (last.getStatusId() == 5) {
                            return "passed";
                        }
                        else {
                            return "failed";
                        }
                    }
                    else if ("Previous".equals(col)) {
                        TestExecution previous = (TestExecution) item.getItemProperty("Previous").getValue();
                        if ((previous == null) || (previous.getStatusId() == null)) {
                            return "not_run";
                        }
                        else if (previous.getStatusId() == 5) {
                            return "passed";
                        }
                        else {
                            return "failed";
                        }
                    }
                    else {
                        return "not_run";
                    }
                }
                else {
                    return "not_run";
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
                    last,
                    previous
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
