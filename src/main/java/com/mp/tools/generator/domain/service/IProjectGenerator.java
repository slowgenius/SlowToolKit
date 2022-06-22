package com.mp.tools.generator.domain.service;

import com.intellij.openapi.project.Project;
import com.mp.tools.generator.domain.model.vo.ProjectConfigVO;

public interface IProjectGenerator {

    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);



}
