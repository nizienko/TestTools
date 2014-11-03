package TestTools.database;

import TestTools.database.build.Build;
import TestTools.database.build.BuildDao;
import TestTools.database.project.Project;
import TestTools.database.project.ProjectDao;
import TestTools.database.testTable.TestTable;
import TestTools.database.testTable.TestTableDao;
import TestTools.database.version.Version;
import TestTools.database.version.VersionDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;


/**
 * Created by def on 22.10.14.
 */
public class DataBaseTests {
    @Test
    public void test1(){
        //Get the Spring Context
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
        TestTableDao testTableDao = (TestTableDao) ctx.getBean("testTableDao");
        TestTable testTable1 = new TestTable();
        testTable1.setTest("Test12");
        testTable1.setStatus(2);
        testTableDao.insert(testTable1);
        List<TestTable> data = testTableDao.select();
        for (TestTable testTable : data){
            System.out.println(testTable.getId() + " " + testTable.getTest() + " " + testTable.getStatus());
        }
    }

    @Test
    public void test2(){
        System.out.println("Test2");
    }

    @Test
    public void createProjectTable(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
        ProjectDao projectDao = (ProjectDao) ctx.getBean("projectDao");
        projectDao.createTable();
        Project myProject = new Project("Проверка", "Тестовая проверка 1");
        System.out.println("insert");
        projectDao.insert(myProject);
        int lastId = 0;
        for (Project project: projectDao.selectAll()){
            System.out.println(project.getId() + " " + project.getName() + " " + project.getDescription());
            lastId = project.getId();
        }
        Project newProject = new Project("Тестовый проект", "Последний " + lastId);
        newProject.setId(lastId);
        System.out.println("update");
        projectDao.update(newProject);
        for (Project project: projectDao.selectAll()){
            System.out.println(project.getId() + " " + project.getName() + " " + project.getDescription());
        }
        System.out.println("delete");
        projectDao.delete(newProject);
        for (Project project: projectDao.selectAll()){
            System.out.println(project.getId() + " " + project.getName() + " " + project.getDescription());
        }
    }
    @Test
    public void createVersionAndBuildTable(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
        VersionDao versionDao = (VersionDao) ctx.getBean("versionDao");
        ProjectDao projectDao = (ProjectDao) ctx.getBean("projectDao");
        projectDao.createTable();
        versionDao.createTable();
        Project project = projectDao.select(1);
        Version version = new Version(project.getId(), "Версия 1", "Описание");
        versionDao.insert(version);
        for (Version v: versionDao.selectByProject(project)){
            System.out.println(v.getId() + " " + v.getName() + " " + v.getDescription());
        }
        BuildDao buildDao = (BuildDao) ctx.getBean("buildDao");
        buildDao.createTable();
        version = versionDao.selectByProject(project).get(0);
        Build build = new Build(version.getId(),
                "Билд 1",
                "Описание",
                new Date(),
                new Date(),
                new Date());
        buildDao.insert(build);
    }
}
