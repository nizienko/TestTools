package TestTools.rest;

import TestTools.database.testexecution.TestExecution;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by def on 04.11.14.
 * input parameters:
 * project
 * version
 * build
 * execution
 * testcaseid
 * status
 */
public class AddTestExecution extends RestServlet {

    public void process(Map<String, String[]> in, PrintWriter out) {
        TestExecution te = null;
        try {
            te = parse(in);
            testManager.addTestExecution(te);
            out.print(buildAnswer("0", "success"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            out.print(buildAnswer("-1", "parameter missing"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            out.print(buildAnswer("-1", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            out.print(buildAnswer("-1", "error"));
        }
    }

    private TestExecution parse(Map<String, String[]> parameterMap) throws IllegalArgumentException {
        TestExecution te = new TestExecution();
        try {
            te.setPtojectName(parameterMap.get("project")[0]);
            te.setVersionName(parameterMap.get("version")[0]);
            te.setBuildName(parameterMap.get("build")[0]);
            te.setExecutionName(parameterMap.get("execution")[0]);
            te.setTestCaseIssue(parameterMap.get("issue")[0]);
            te.setTestCaseName(parameterMap.get("name")[0]);
            te.setStatusId(Integer.parseInt(parameterMap.get("status")[0]));
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return te;
    }

}
