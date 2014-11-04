package TestTools.core;

import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.version.Version;
import TestTools.vaadin.Observer;

import java.util.ArrayList;

/**
 * Created by def on 04.11.14.
 */
public class Notifier implements Subject {
    ArrayList<Observer> observers = new ArrayList<Observer>();

    public void registerObserver(Observer o) {
        observers.add(o);
        System.out.println(observers.size());
    }

    public void removeObservers(Observer o) {
        observers.remove(o);
    }

    public void notifyObervers(Project project, Version version, Build build, BuildExecution buildExecution, TestCase testCase, TestExecution testExecution) {
        for (Observer o : observers) {
            o.addTestResult(project, version, build, buildExecution, testCase, testExecution);
        }
    }
}
