package TestTools.publisher.Zephyr;

import TestTools.core.AbstractDaemon;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.version.Version;
import TestTools.publisher.PublisherImpl;
import TestTools.testmanager.TestManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by def on 23.11.14.
 */
public class ZephyrPublisher extends AbstractDaemon implements PublisherImpl {
    public static final Logger LOG = Logger.getLogger(ZephyrPublisher.class);
    private Queue<Product> productsQueue;
    private Queue<Cycle> cyclesQueue;
    private Queue<TestExecution> testsQueue;
    private TestManager testManager;
    private ZAPIActions zapiActions;
    private String project;
    private String jiraVersion;
    private ArrayList<HashMap<String, String>> versionList;

    public ZephyrPublisher(Integer period) {
        super(period);
        productsQueue = new LinkedList<Product>();
        cyclesQueue = new LinkedList<Cycle>();
        testsQueue = new LinkedList<TestExecution>();
    }

    public void setTestManager(TestManager testManager){
        this.testManager = testManager;
        try {
            zapiActions = new ZAPIActions(testManager);
            testManager.addPublisher(this);
        }
        catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            super.stop();
        }
    }

    @Override
    protected void process() {
        LOG.info("Products in queue: " + productsQueue.size());
        while( !productsQueue.isEmpty() ){
            Product newProduct = productsQueue.remove();
            LOG.info("Inserting project " + newProduct.getName());
            zapiActions.publicateProduct(newProduct);
        }
        LOG.info("Cycles in queue: " + cyclesQueue.size());
        while( !cyclesQueue.isEmpty() ){
            Cycle newCycle = cyclesQueue.remove();
            LOG.info("Inserting cycle " + newCycle.getName());
            zapiActions.publicateCycle(newCycle);
        }
        LOG.info("Tests in queue: " + testsQueue.size());
        while( !testsQueue.isEmpty() ){
            TestExecution newTest = testsQueue.remove();
            LOG.info("Inserting test " + newTest.getTestCaseName());
            zapiActions.publicateTest(newTest);
        }
        try {
            if (!testManager.isAlife()){
                super.stop();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            super.stop();
        }
    }

    public void publicateProject(Project project) {
        LOG.info("New project " + project.getName() + ", do nothing");
    }

    public void publicateVersion(Project project, Version version) {
        Product product = new Product(project.getName() + "-" + version.getName());
        LOG.info("New version, publicating product " + product.getName());
        productsQueue.add(product);
    }

    public void publicateBuild(Project project, Version version, Build build) {
        Cycle cycle = new Cycle(new Product(project.getName() + "-" + version.getName()), build.getName());
        LOG.info("New build, publicating cycle " + cycle.getName() + " for product " + cycle.getProduct().getName());
        cyclesQueue.add(cycle);
    }

    public void publicateBuildExecution(Project project, Version version, Build build, BuildExecution buildExecution) {
        LOG.info("New build execution " + buildExecution.getName() + ", do nothing");
    }

    public void publicateTestExecution(Project project, Version version, Build build, BuildExecution buildExecution, TestExecution testExecution) {
        LOG.info("New test execution " + testExecution.getTestCaseName());
        testsQueue.add(testExecution);
    }
}
