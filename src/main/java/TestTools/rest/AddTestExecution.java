package TestTools.rest;

import TestTools.core.MainApp;
import TestTools.core.Notifier;
import TestTools.database.DaoContainer;
import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testcase.TestCase;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.version.Version;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
public class AddTestExecution extends HttpServlet {
    DaoContainer daoContainer;
    Notifier notifier;

    @Override
    public void init() {
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        notifier = (Notifier) MainApp.getCtx().getBean("notifier");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            try {
                addTestExecution(request.getParameterMap());
                printWriter.print(buildAnswer("0", "success"));
            } catch (NullPointerException e) {
                e.printStackTrace();
                printWriter.print(buildAnswer("-1", "parameter missing"));
            } catch (EmptyResultDataAccessException e) {
                e.printStackTrace();
                printWriter.print(buildAnswer("-1", "no such testCaseId"));
            } catch (Exception e) {
                e.printStackTrace();
                printWriter.print(buildAnswer("-1", "error"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTestExecution(Map<String, String[]> parameterMap) {
        String projectName = parameterMap.get("project")[0];
        String versionName = parameterMap.get("version")[0];
        String buildName = parameterMap.get("build")[0];
        String execution = parameterMap.get("execution")[0];
        Integer testCaseId = Integer.parseInt(parameterMap.get("testcaseid")[0]);
        Integer status = Integer.parseInt(parameterMap.get("status")[0]);

        TestCase testCase = daoContainer.getTestCaseDao().select(testCaseId);
        System.out.println(testCase.getName());

        Project project = null;
        try {
            project = daoContainer.getProjectDao().selectByName(projectName);
        } catch (EmptyResultDataAccessException e) {
            project = new Project(projectName, "created by test");
            daoContainer.getProjectDao().insert(project);
            project = daoContainer.getProjectDao().selectByName(projectName);
        }

        Version version = null;
        try {
            version = daoContainer.getVersionDao().selectByProjectAndName(project, versionName);
        } catch (EmptyResultDataAccessException e) {
            version = new Version(project.getId(), versionName, "created by test");
            daoContainer.getVersionDao().insert(version);
            version = daoContainer.getVersionDao().selectByProjectAndName(project, versionName);
        }

        Build build = null;
        try {
            build = daoContainer.getBuildDao().selectByVersionAndName(version, buildName);
        } catch (EmptyResultDataAccessException e) {
            build = new Build(version.getId(), buildName, "created by test", new Date(), new Date(), new Date());
            daoContainer.getBuildDao().insert(build);
            build = daoContainer.getBuildDao().selectByVersionAndName(version, buildName);
        }
        BuildExecution buildExecution = null;
        try {
            buildExecution = daoContainer.getBuildExecutionDao().selectByBuildAndName(build, execution);
        } catch (EmptyResultDataAccessException e) {
            buildExecution = new BuildExecution(build.getId(), execution);
            daoContainer.getBuildExecutionDao().insert(buildExecution);
            buildExecution = daoContainer.getBuildExecutionDao().selectByBuildAndName(build, execution);
        }
        TestExecution testExecution = new TestExecution(testCase.getId(), status, buildExecution.getId());
        daoContainer.getTestExecutionDao().insert(testExecution);
        notifier.notifyObervers(project, version, build, buildExecution, testCase, testExecution);
    }

    private String buildAnswer(String status, String message) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            if (message != null) {
                jsonObject.put("message", message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
