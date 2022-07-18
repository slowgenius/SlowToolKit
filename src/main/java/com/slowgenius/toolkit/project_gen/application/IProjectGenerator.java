package com.slowgenius.toolkit.project_gen.application;

import com.intellij.openapi.project.Project;
import com.slowgenius.toolkit.project_gen.domain.model.ProjectConfigVO;

public interface IProjectGenerator extends Generator {

    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);


}
