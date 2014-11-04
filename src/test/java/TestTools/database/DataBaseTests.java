package TestTools.database;

import TestTools.core.MainApp;
import TestTools.database.build.Build;
import TestTools.database.project.Project;
import TestTools.database.version.Version;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.Date;


/**
 * Created by def on 22.10.14.
 */
public class DataBaseTests {

    @Test
    public void createVersionAndBuildTable() {
        ApplicationContext ctx = MainApp.getCtx();
        DaoContainer daoContainer = (DaoContainer) ctx.getBean("daoContainer");
        daoContainer.getProjectDao().createTable();
        daoContainer.getProjectDao().createTable();
        Project project = daoContainer.getProjectDao().select(1);
        Version version = new Version(project.getId(), "Версия 1", "Описание");
        daoContainer.getVersionDao().insert(version);
        for (Version v : daoContainer.getVersionDao().selectByProject(project)) {
            System.out.println(v.getId() + " " + v.getName() + " " + v.getDescription());
        }
        daoContainer.getBuildDao().createTable();
        version = daoContainer.getVersionDao().selectByProject(project).get(0);
        Build build = new Build(version.getId(),
                "Билд 1",
                "Описание",
                new Date(),
                new Date(),
                new Date());
        daoContainer.getBuildDao().insert(build);
    }

    @Test
    public void createTables() {
        // create tables
        DaoContainer daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        daoContainer.getBuildDao().createTable();
        daoContainer.getBuildExecutionDao().createTable();
        daoContainer.getProjectDao().createTable();
        daoContainer.getTestCaseDao().createTable();
        daoContainer.getTestExecutionDao().createTable();
        daoContainer.getTestSuiteDao().createTable();
        daoContainer.getVersionDao().createTable();
    }
}
