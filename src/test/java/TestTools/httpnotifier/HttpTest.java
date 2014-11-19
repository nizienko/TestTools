package TestTools.httpnotifier;

import TestTools.core.MainApp;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by def on 07.11.14.
 */
public class HttpTest {

    @Test
    public void getRequest() {
        HttpNotifier httpNotifier = (HttpNotifier) MainApp.getCtx().getBean("httpNotifier");
        for (int i = 0; i < 1000; i++) {
            httpNotifier.sendGet(addTestExecution1.replace("${i}", i + ""));
            httpNotifier.sendGet(addTestExecution2.replace("${i}", i + ""));
        }
    }

    @Test
    public void postRequest() {
        HttpNotifier httpNotifier = (HttpNotifier) MainApp.getCtx().getBean("httpNotifier");
        int j = 1;
//        for (int y = 0; y < 5; y++) {
            for (int i = 50; i < 80; i++) {
                Map params = new HashMap();
                params.put("project", "penelope");
                params.put("version", "3.37");
                params.put("build", "12402");
                params.put("execution", "functional");
                params.put("status", (i + 11) % 2);
                params.put("name", "Пенелопный тест " + i);
                params.put("issue", "TC-" + i);
                System.out.println(j + ". " + httpNotifier.sendPost(url, params));
                j++;
            }
//        }
    }

    private final static String url = "http://jenkins-ot.test.yamoney.ru/ttools/rest/addtestexecution";
    private final static String addTestExecution1 = "http://localhost:8080/TestTools/rest/addtestexecution?project=penelope&version=3.45&build=123${i}&execution=integration&status=1&issue=TC-${i}&name=Тест-${i}";
    private final static String addTestExecution2 = "http://localhost:8080/TestTools/rest/addtestexecution?project=calypso&version=3.45&build=177${i}&execution=integration&status=1&issue=CK-${i}&name=Тест-${i}";

}
