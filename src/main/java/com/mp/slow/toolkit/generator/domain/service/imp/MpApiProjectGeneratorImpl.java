package com.mp.slow.toolkit.generator.domain.service.imp;

import com.intellij.openapi.project.Project;
import com.mp.slow.toolkit.generator.domain.model.ProjectConfigVO;
import com.mp.slow.toolkit.generator.domain.service.AbstractProjectGenerator;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/24 20:17:53
 */

public class MpApiProjectGeneratorImpl extends AbstractProjectGenerator {

    @Override
    public void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig) {
        generateMpProject(project, entryPath, projectConfig);
        generateUtil(project, entryPath, projectConfig);
        generateService(project, entryPath, projectConfig);
        generateIntegration(project, entryPath, projectConfig);
        generateWeb(project, entryPath, projectConfig);
    }

    public void generateMpProject(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "/", entryPath, "pom.xml", "api-parent-pom.ftl", projectConfig);
    }

    public void generateUtil(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".util", entryPath + "/app/common/" + projectConfig.getProjectName() + "-util", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/common/" + projectConfig.getProjectName() + "-util", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/common/" + projectConfig.getProjectName() + "-util", "pom.xml", "api-util-pom.ftl", projectConfig);
    }

    public void generateService(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".service", entryPath + "/app/service/" + projectConfig.getProjectName() + "-service", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/service/" + projectConfig.getProjectName() + "-service", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/service/" + projectConfig.getProjectName() + "-service", "pom.xml", "api-service-pom.ftl", projectConfig);
    }

    public void generateIntegration(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".integration", entryPath + "/app/service/" + projectConfig.getProjectName() + "-integration", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/service/" + projectConfig.getProjectName() + "-integration", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/service/" + projectConfig.getProjectName() + "-integration", "pom.xml", "api-integration-pom.ftl", projectConfig);
    }

    public void generateWeb(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".web", entryPath + "/" + projectConfig.getProjectName() + "-web", "Application.java", "api-Application.java.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/" + projectConfig.getProjectName() + "-web", "application.yml", "api-config-application.yml.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/" + projectConfig.getProjectName() + "-web", "application-dev.yml", "api-config-application-dev.yml.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/" + projectConfig.getProjectName() + "-web", "application-test.yml", "api-config-application-test.yml.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/" + projectConfig.getProjectName() + "-web", "pom.xml", "api-web-pom.ftl", projectConfig);
    }
}
