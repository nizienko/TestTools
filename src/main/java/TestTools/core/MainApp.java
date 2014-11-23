package TestTools.core;

import TestTools.database.DaoContainer;
import TestTools.publisher.Zephyr.ZephyrPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by def on 04.11.14.
 */
public class MainApp {
    private static ApplicationContext ctx = null;

    private MainApp() {

    }

    public synchronized static ApplicationContext getCtx() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("Beans.xml");
            DaoContainer daoContainer = (DaoContainer) ctx.getBean("daoContainer");
            daoContainer.getBuildDao().createTable();
            daoContainer.getBuildExecutionDao().createTable();
            daoContainer.getProjectDao().createTable();
            daoContainer.getTestCaseDao().createTable();
            daoContainer.getTestExecutionDao().createTable();
            daoContainer.getTestSuiteDao().createTable();
            daoContainer.getVersionDao().createTable();
            daoContainer.getTestSettingDao().createTable();
            ZephyrPublisher zephyrPublisher = (ZephyrPublisher) ctx.getBean("publisher");
            Thread zephyrThread = new Thread(zephyrPublisher);
            zephyrThread.start();
        }
        return ctx;
    }
}
