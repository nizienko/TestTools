package TestTools.vaadin;

import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.version.Version;

/**
 * Created by def on 04.11.14.
 */
public interface Observer {
    public void addTestResult(Project project, Version version,
                              Build build, BuildExecution buildExecution,
                              TestCase testCase, TestExecution testExecution);
}
