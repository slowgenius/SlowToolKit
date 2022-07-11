package com.slowgenius.toolkit.generator.application;

import com.intellij.openapi.project.Project;
import com.slowgenius.toolkit.generator.domain.model.ProjectConfigVO;

public interface IProjectGenerator extends Generator {

    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);


}
