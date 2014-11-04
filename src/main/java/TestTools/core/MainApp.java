package TestTools.core;

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
        }
        return ctx;
    }
}
