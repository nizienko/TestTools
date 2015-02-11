package TestTools.core;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by nizienko on 05.12.14.
 */
public class ContextListener implements ServletContextListener {
    public static final Logger LOG = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("Ttools started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (MainApp.isRunning()) {
            LOG.info("Ttools stoped");
            MainApp.stop();
        }
    }
}
