package TestTools.core;

import TestTools.database.DaoContainer;
import TestTools.publisher.Zephyr.ZephyrPublisher;
import TestTools.testmanager.TestManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by def on 04.11.14.
 */
public class MainApp {
    private static ApplicationContext ctx = null;
    private static boolean running;
    private MainApp() {

    }

    public synchronized static ApplicationContext getCtx() {
        if (ctx == null) {
            running = true;
            ctx = new ClassPathXmlApplicationContext("Beans.xml");
            init();
        }
        return ctx;
    }

    public synchronized static void stop(){
        TestManager testManager = (TestManager) ctx.getBean("testManager");
        testManager.stop();
        running = false;
        ((ConfigurableApplicationContext) ctx).close();
    }

    private static void init(){
        DaoContainer daoContainer = (DaoContainer) ctx.getBean("daoContainer");
        daoContainer.getBuildDao().createTable();
        daoContainer.getBuildExecutionDao().createTable();
        daoContainer.getProjectDao().createTable();
        daoContainer.getTestCaseDao().createTable();
        daoContainer.getTestExecutionDao().createTable();
        daoContainer.getTestSuiteDao().createTable();
        daoContainer.getVersionDao().createTable();
        daoContainer.getTestSettingDao().createTable();
        daoContainer.getUserDao().createTable();
        daoContainer.getSystemSettingsDao().createTable();

        // Starting zephyr publisher
        new Thread((ZephyrPublisher) ctx.getBean("publisher")).start();
    }

    public static boolean isRunning() {
        return running;
    }
}
