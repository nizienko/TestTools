package TestTools.publisher;

import TestTools.database.build.Build;
import TestTools.database.buildexecution.BuildExecution;
import TestTools.database.project.Project;
import TestTools.database.testexecution.TestExecution;
import TestTools.database.version.Version;

/**
 * Created by def on 23.11.14.
 */
public interface PublisherImpl {
    public void publicateProject(Project project);

    public void publicateVersion(Project project, Version version);

    public void publicateBuild(Project project, Version version, Build build);

    public void publicateBuildExecution(Project project, Version version, Build build, BuildExecution buildExecution);

    public void publicateTestExecution(Project project, Version version, Build build, BuildExecution buildExecution, TestExecution testExecution);


}
