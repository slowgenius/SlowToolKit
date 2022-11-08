package com.slowgenius.toolkit.gen.project.domain.service;

import com.intellij.openapi.project.Project;
import com.slowgenius.toolkit.gen.project.domain.model.ProjectConfigVO;

public interface IProjectGenerator  {

    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);


}
