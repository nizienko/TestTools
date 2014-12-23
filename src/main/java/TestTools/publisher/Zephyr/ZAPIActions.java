package TestTools.publisher.Zephyr;

import TestTools.database.testexecution.TestExecution;
import TestTools.testmanager.TestManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nizienko on 23.12.14.
 */
public class ZAPIActions {
    public static final Logger LOG = Logger.getLogger(ZephyrPublisher.class);

    private TestManager testManager;
    private ZAPIUtils zapiUtils;
    private String project;
    private String projectKey;
    private ArrayList<HashMap<String, String>> jiraVersions;
    private HashMap<String, ArrayList<HashMap<String, String>>> jiraCycles;
    public ZAPIActions(TestManager testManager){
        this.testManager = testManager;
        try {
            project = testManager.getSetting("jira.project");
            projectKey = testManager.getSetting("jira.project.key");
            this.zapiUtils = new ZAPIUtils(testManager.getSetting("jira.url"),
                    testManager.getSetting("jira.account"));
            this.jiraVersions = zapiUtils.getJiraVersions(projectKey);
            jiraCycles = new HashMap<String, ArrayList<HashMap<String, String>>>();
        }
        catch (NullPointerException e){
            throw new IllegalStateException("Can't load system settings: jira.url, jira.account, jira.project, jira.project.key. Check them.");
        }
    }

    public void publicateProduct(Product product) {
        int i = 0;
        while (!isProductExist(product)){
            i++;
            String result = zapiUtils.createVersion(product.getName(), projectKey);
            LOG.info("Creating product " + product.getName() + "(" + i + "): " + result);
            this.jiraVersions = zapiUtils.getJiraVersions(projectKey);
            if (!isProductExist(product)) {
                LOG.info("Failed");
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isProductExist(Product product){
        boolean isExist = false;
        for (HashMap<String, String> version : jiraVersions) {
            if (version.get("name").equals(product.getName())) {
                isExist = true;
            }
        }
        return isExist;
    }
    private boolean isCycleExist(Cycle cycle, ArrayList<HashMap<String, String>> jiraCycles){
        boolean isExist = false;
        for (HashMap<String, String> jiraCycle : jiraCycles) {
            if (jiraCycle.get("name").equals(cycle.getName())) {
                isExist = true;
            }
        }
        return isExist;
    }

    private HashMap<String, String> getJiraVersion(Product product){
        for (HashMap<String, String> version : jiraVersions) {
            if (version.get("name").equals(product.getName())) {
                return version;
            }
        }
        return null;
    }

    public void publicateCycle(Cycle cycle) {
        if (!isProductExist(cycle.getProduct())){
            publicateProduct(cycle.getProduct());
        }
        HashMap<String, String> jiraVersion = getJiraVersion(cycle.getProduct());
        if (!jiraCycles.containsKey(cycle.getProduct().getName())){
            jiraCycles.put(cycle.getProduct().getName(), zapiUtils.getCycles(project, cycle.getProduct().getName()));
        }
        if (!isCycleExist(cycle, jiraCycles.get(cycle.getProduct().getName()))){
            int i = 0;
            while (!isCycleExist(cycle, jiraCycles.get(cycle.getProduct().getName()))){
                i++;
                String result = zapiUtils.createCycle(project, jiraVersion.get("value"), cycle.getName(), jiraVersion.get("label"));
                LOG.info("Creating cycle " + cycle.getName() + "(" + i + ") in product " + jiraVersion.get("value") + ": " + result);
                jiraCycles.remove(cycle.getProduct().getName());
                jiraCycles.put(cycle.getProduct().getName(), zapiUtils.getCycles(project, cycle.getProduct().getName()));
                if (!isCycleExist(cycle, jiraCycles.get(cycle.getProduct().getName()))) {
                    LOG.info("Failed");
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void publicateTest(TestExecution test) {

    }
}
