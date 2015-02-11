package TestTools.core;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Created by def on 11.02.15.
 */
public class EmbeddedServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        Connector connector = new SelectChannelConnector();
        connector.setPort(8080);
        server.addConnector(connector);

        WebAppContext root = new WebAppContext("src/main/webapp", "/");
        server.setHandlers(new Handler[]{root});

        server.start();
        MainApp.getCtx();
        while(MainApp.isRunning()) {
            Thread.sleep(15000);
        }
        server.stop();
    }
}
