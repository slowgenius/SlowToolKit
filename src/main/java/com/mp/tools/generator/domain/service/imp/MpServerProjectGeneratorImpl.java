package com.mp.tools.generator.domain.service.imp;

import com.intellij.openapi.project.Project;
import com.mp.tools.generator.domain.model.vo.ProjectConfigVO;
import com.mp.tools.generator.domain.service.AbstractProjectGenerator;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/22 21:08:44
 */

public class MpServerProjectGeneratorImpl extends AbstractProjectGenerator {

    @Override
    public void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig) {
        generateMpProject(project, entryPath, projectConfig);
        generateDal(project, entryPath, projectConfig);
        generateUtil(project, entryPath, projectConfig);
        generateModel(project, entryPath, projectConfig);
        generateService(project, entryPath, projectConfig);
        generateBiz(project, entryPath, projectConfig);
        generateFacade(project, entryPath, projectConfig);
        generateIntegration(project, entryPath, projectConfig);
        generateWeb(project, entryPath, projectConfig);
    }

    public void generateMpProject(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "/", entryPath, "pom.xml", "service-parent-pom.ftl", projectConfig);
    }


    public void generateDal(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".dal", entryPath + "/app/common/" + projectConfig.getProjectName() + "-dal", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/common/" + projectConfig.getProjectName() + "-dal", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/common/" + projectConfig.getProjectName() + "-dal", "pom.xml", "service-dal-pom.ftl", projectConfig);
    }

    public void generateUtil(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".util", entryPath + "/app/common/" + projectConfig.getProjectName() + "-util", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/common/" + projectConfig.getProjectName() + "-util", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/common/" + projectConfig.getProjectName() + "-util", "pom.xml", "service-util-pom.ftl", projectConfig);
    }

    public void generateModel(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".model", entryPath + "/app/core/" + projectConfig.getProjectName() + "-model", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".model.enums", entryPath + "/app/core/" + projectConfig.getProjectName() + "-model", "ErrorCodeEnum.java", "ErrorCodeEnum.java.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/core/" + projectConfig.getProjectName() + "-model", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/core/" + projectConfig.getProjectName() + "-model", "pom.xml", "service-model-pom.ftl", projectConfig);

    }

    public void generateService(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage()  + ".service", entryPath + "/app/core/" + projectConfig.getProjectName() + "-service", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/core/" + projectConfig.getProjectName() + "-service", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/core/" + projectConfig.getProjectName() + "-service", "pom.xml", "service-service-pom.ftl", projectConfig);
    }

    public void generateBiz(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".biz", entryPath + "/app/" + projectConfig.getProjectName() + "-biz", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/" + projectConfig.getProjectName() + "-biz", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/" + projectConfig.getProjectName() + "-biz", "pom.xml", "service-biz-pom.ftl", projectConfig);
    }

    public void generateFacade(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".facade", entryPath + "/app/service/" + projectConfig.getProjectName() + "-facade", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/service/" + projectConfig.getProjectName() + "-facade", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/service/" + projectConfig.getProjectName() + "-facade", "pom.xml", "service-facade-pom.ftl", projectConfig);
    }

    public void generateIntegration(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".integration", entryPath + "/app/service/" + projectConfig.getProjectName() + "-integration", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/app/service/" + projectConfig.getProjectName() + "-integration", ".gitkeep", "gitkeep.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/app/service/" + projectConfig.getProjectName() + "-integration", "pom.xml", "service-integration-pom.ftl", projectConfig);
    }

    public void generateWeb(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.getBasePackage() + ".web", entryPath + "/" + projectConfig.getProjectName() + "-web", "Application.java", "service-Application.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/" + projectConfig.getProjectName() + "-web", "application.yml", "service-config-application.yml.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/" + projectConfig.getProjectName() + "-web", "application-dev.yml", "service-config-application-dev.yml.ftl", projectConfig);
        writeFile(project, "src/main/resources", entryPath + "/" + projectConfig.getProjectName() + "-web", "application-test.yml", "service-config-application-test.yml.ftl", projectConfig);
        writeFile(project, "/", entryPath + "/" + projectConfig.getProjectName() + "-web", "pom.xml", "service-web-pom.ftl", projectConfig);
    }

}
