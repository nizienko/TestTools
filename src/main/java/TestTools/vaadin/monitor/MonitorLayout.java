package TestTools.vaadin.monitor;

import TestTools.core.MainApp;
import TestTools.core.Notifier;
import TestTools.database.DaoContainer;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.version.Version;
import TestTools.vaadin.Observer;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by def on 04.11.14.
 */
public class MonitorLayout extends VerticalLayout implements Observer {
    DaoContainer daoContainer;
    Notifier notifier;

    public MonitorLayout() {
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        notifier = (Notifier) MainApp.getCtx().getBean("notifier");
//        notifier.registerObserver(this);
        for (TestExecution testExecution : daoContainer.getTestExecutionDao().selectLast(10)) {
            TestCase testCase = daoContainer.getTestCaseDao().select(testExecution.getTestCaseId());
            this.addComponent(new Label("Test " + testExecution.getId() + " " + testExecution.getStatusId() + " " + testCase.getName()));
        }
    }

    public void addTestResult(Project project, Version version, Build build, BuildExecution buildExecution, TestCase testCase, TestExecution testExecution) {
        System.out.println("Adding label!!");
        this.addComponent(new Label("Test " + testExecution.getId() + " " + testExecution.getStatusId() + " " + testCase.getName()));
    }
}
