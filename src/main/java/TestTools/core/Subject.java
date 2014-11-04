package TestTools.core;

import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.version.Version;
import TestTools.vaadin.Observer;

/**
 * Created by def on 04.11.14.
 */
public interface Subject {
    public void registerObserver(Observer o);

    public void removeObservers(Observer o);

    public void notifyObervers(Project project, Version version,
                               Build build, BuildExecution buildExecution,
                               TestCase testCase, TestExecution testExecution);
}
