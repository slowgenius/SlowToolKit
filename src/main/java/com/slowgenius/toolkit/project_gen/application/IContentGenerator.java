package com.slowgenius.toolkit.project_gen.application;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/6/25 22:51:44
 */
public interface IContentGenerator extends Generator {

    void doGenerate(Project project, DataContext dataContext);

}