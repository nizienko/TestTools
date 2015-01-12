package TestTools.publisher.Zephyr;

import TestTools.database.testexecution.TestExecution;
import TestTools.testmanager.TestManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nizienko on 23.12.14.
 */
public class ZAPIActions {
    public static final Logger LOG = Logger.getLogger(ZAPIActions.class);

    private ZAPIUtils zapiUtils;
    private String project;
    private String projectKey;
    private ArrayList<HashMap<String, String>> products;
    private HashMap<String, ArrayList<HashMap<String, String>>> cylces; // key is product name
    private HashMap<Integer, ArrayList<HashMap<String, String>>> issues; // key is cycleId
    public ZAPIActions(TestManager testManager){
        try {
            project = testManager.getSetting("jira.project");
            projectKey = testManager.getSetting("jira.project.key");
            this.zapiUtils = new ZAPIUtils(testManager.getSetting("jira.url"),
                    testManager.getSetting("jira.account"));
            this.products = zapiUtils.getVersions(project);
            LOG.info(products);
            cylces = new HashMap<String, ArrayList<HashMap<String, String>>>();
            issues = new HashMap<Integer, ArrayList<HashMap<String, String>>>();
        }
        catch (Exception e){
            LOG.error(e.getMessage());
            throw new IllegalStateException("Can't load system settings: jira.url, jira.account, jira.project, jira.project.key. Check them.");
        }
    }

    public void publicateProduct(Product product) {
        if (!isProductExist(product)){
            String result = zapiUtils.createVersion(product.getName(), projectKey);
            LOG.info("Creating product " + product.getName() + ": " + result);
            this.products = zapiUtils.getVersions(project);
            LOG.info(products);
        }
        else {
            LOG.info(product.getName() + " already exists");
        }
    }

    private boolean isProductExist(Product product){
        boolean isExist = false;
        try {
        for (HashMap<String, String> version : products) {
            if (version.get("label").equals(product.getName())) {
                isExist = true;
            }
        }
        }
        catch (NullPointerException e){
            isExist = false;
        }
        return isExist;
    }
    private boolean isCycleExist(Cycle cycle, ArrayList<HashMap<String, String>> jiraCycles){
        boolean isExist = false;
        LOG.info("is exist " + jiraCycles);
        try {
            for (HashMap<String, String> jiraCycle : jiraCycles) {
                if (jiraCycle.get("name").equals(cycle.getName())) {
                    isExist = true;
                }
            }
        }
        catch (NullPointerException e) {
            isExist = false;
        }
        return isExist;
    }

    private boolean isIssueExist(String issue, Integer cycleId){
        boolean isExist = false;
        LOG.info("is exist " + issue);
        try {
            for (HashMap<String, String> i : issues.get(cycleId)) {
                if (i.get("issueKey").equals(issue)) {
                    isExist = true;
                }
            }
        }
        catch (NullPointerException e) {
            isExist = false;
        }
        return isExist;
    }

    private HashMap<String, String> getJiraVersion(Product product){
        for (HashMap<String, String> version : products) {
            if (version.get("label").equals(product.getName())) {
                return version;
            }
        }
        return null;
    }

    private HashMap<String, String> getJiraCycle(Product product, Cycle cycle){
        for (HashMap<String, String> c : cylces.get(product.getName())) {
            if (c.get("name").equals(cycle.getName())) {
                return c;
            }
        }
        return null;
    }

    private HashMap<String, String> getJiraIssue(String issue, Integer cycleId){
        for (HashMap<String, String> i : issues.get(cycleId)) {
            if (i.get("issueKey").equals(issue)) {
                return i;
            }
        }
        return null;
    }

    public void publicateCycle(Cycle cycle) {
        if (!isProductExist(cycle.getProduct())){
            publicateProduct(cycle.getProduct());
        }
        HashMap<String, String> jiraVersion = getJiraVersion(cycle.getProduct());
        if (!cylces.containsKey(cycle.getProduct().getName())){
            cylces.put(cycle.getProduct().getName(), zapiUtils.getCycles(project, jiraVersion.get("value")));
        }
        LOG.info("Cycles: " + cylces.get(cycle.getProduct().getName()));
        if (!isCycleExist(cycle, cylces.get(cycle.getProduct().getName()))){
            String result = zapiUtils.createCycle(project, getJiraVersion(cycle.getProduct()).get("value"),
                    cycle.getName(), getJiraVersion(cycle.getProduct()).get("label"));
            LOG.info("Creating cycle " + cycle.getName() + " in product " +
                    getJiraVersion(cycle.getProduct()).get("value") + ": " + result);
            cylces.remove(cycle.getProduct().getName());
            cylces.put(cycle.getProduct().getName(), zapiUtils.getCycles(project, jiraVersion.get("value")));
        }
        else {
            LOG.info("Cycle " + cycle.getName() + " already exist");
        }
    }

    public void publicateTest(TestExecution test) {
        LOG.info("Test execution: " + test.getTestCaseIssue() + " " + test.getTestCaseName() + " - " + test.getStatusId());
        Product product = new Product(test.getPtojectName() + "-" + test.getVersionName());
        Cycle cycle = new Cycle(product, test.getBuildName());
        HashMap<String, String> jiraVersion;
        if (isProductExist(product)){
            jiraVersion = getJiraVersion(product);
            LOG.info("Product found: " + jiraVersion);
        }
        else {
            LOG.info("Product not found");
            publicateProduct(product);
            jiraVersion = getJiraVersion(product);
            LOG.info("Product created: " + jiraVersion);
        }
        HashMap<String, String> jiraCycle;
        if (isCycleExist(cycle, cylces.get(product.getName()))) {
            jiraCycle = getJiraCycle(product, cycle);
            LOG.info("Cycle found: " + jiraCycle);
        }
        else {
            LOG.info("Cycle not found");
            publicateCycle(cycle);
            jiraCycle = getJiraCycle(product, cycle);
            LOG.info("Checked that cycle exist: " + jiraCycle);
        }
        if (!issues.containsKey(Integer.parseInt(jiraCycle.get("id")))){
            LOG.info("Issue list not found of cycle " + cycle.getName());
            issues.put(Integer.parseInt(jiraCycle.get("id")), zapiUtils.getExecutions(jiraCycle.get("name"), jiraVersion.get("label")));
        }
        HashMap<String, String> jiraIssue;
        if (isIssueExist(test.getTestCaseIssue(), Integer.parseInt(jiraCycle.get("id")))){
            jiraIssue = getJiraIssue(test.getTestCaseIssue(), Integer.parseInt(jiraCycle.get("id")));
        }
        else {
            zapiUtils.addTestsToCycle(test.getTestCaseIssue(), jiraVersion.get("value"), jiraCycle.get("id"), project);
            issues.remove(Integer.parseInt(jiraCycle.get("id")));
            issues.put(Integer.parseInt(jiraCycle.get("id")), zapiUtils.getExecutions(jiraCycle.get("name"), jiraVersion.get("label")));
            jiraIssue = getJiraIssue(test.getTestCaseIssue(), Integer.parseInt(jiraCycle.get("id")));
        }
        zapiUtils.execute(jiraIssue.get("id"), test.getStatusId(), "Коммент");
    }
}
