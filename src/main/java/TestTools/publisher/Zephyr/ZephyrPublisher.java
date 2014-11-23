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

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by def on 23.11.14.
 */
public class ZephyrPublisher extends AbstractDaemon implements PublisherImpl {
    public static final Logger LOG = Logger.getLogger(ZephyrPublisher.class);
    private Queue<Product> products;
    private Queue<Cycle> cycles;
    private Queue<TestExecution> tests;

    public ZephyrPublisher(Integer period) {
        super(period);
        products = new LinkedList<Product>();
        cycles = new LinkedList<Cycle>();
        tests = new LinkedList<TestExecution>();
    }

    public void setTestManager(TestManager testManager){
        testManager.addPublisher(this);
    }

    @Override
    protected void process() {
        LOG.info("Zephyr publisher daemon");
    }

    public void publicateProject(Project project) {
        LOG.info("New project " + project.getName());
    }

    public void publicateVersion(Project project, Version version) {
        LOG.info("New version " + project.getName() + " " + version.getName());
        Product product = new Product(project.getName() + "-" + version.getName());
        products.add(product);
    }

    public void publicateBuild(Project project, Version version, Build build) {
        LOG.info("New build " + build.getName());
        Cycle cycle = new Cycle(new Product(project.getName() + "-" + version.getName()), build.getName());
        cycles.add(cycle);
    }

    public void publicateBuildExecution(Project project, Version version, Build build, BuildExecution buildExecution) {
        LOG.info("New build execution " + buildExecution.getName());
    }

    public void publicateTestExecution(Project project, Version version, Build build, BuildExecution buildExecution, TestExecution testExecution) {
        LOG.info("New test execution " + testExecution.getTestCaseName());
        tests.add(testExecution);
    }
}
