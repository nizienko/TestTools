package TestTools.vaadin.gui.body;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import TestTools.database.testexecution.FullTestExecution;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.omg.CORBA.ARG_IN;

import java.util.List;

/**
 * Created by nizienko on 05.11.14.
 */
public class LatestTestsLayout extends VerticalLayout {
    private Table table;

    public LatestTestsLayout() {
        table = new Table();
        table.setWidth("100%");
        table.setEditable(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.addContainerProperty("Name", String.class, null);
        table.addContainerProperty("Project", String.class, null);
        table.addContainerProperty("Version", String.class, null);
        table.addContainerProperty("Build", String.class, null);
        table.addContainerProperty("Execution", String.class, null);
        table.addContainerProperty("Date", String.class, null);
        table.addContainerProperty("Status", String.class, null);
        DaoContainer daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        int i = 1;
        for (FullTestExecution fte : daoContainer.getFullTestExecutionDao().selectLast(50)) {
            table.addItem(new Object[]{fte.getName(), fte.getPtoject(), fte.getVersion(), fte.getBuild(),
                    fte.getExecution(), fte.getDate(), fte.getStatus()}, new Integer(i));
            i++;
        }
        this.addComponent(table);
    }

    public void updateLatestTests(List<FullTestExecution> fullTestExecutionList) {
        table.removeAllItems();
        int i = 1;
        for (FullTestExecution fte : fullTestExecutionList) {
            table.addItem(new Object[]{fte.getName(), fte.getPtoject(), fte.getVersion(), fte.getBuild(),
                    fte.getExecution(), fte.getDate(), fte.getStatus()}, new Integer(i));
            i++;
        }
    }
}
