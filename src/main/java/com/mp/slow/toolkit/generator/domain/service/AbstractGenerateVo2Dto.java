package com.mp.slow.toolkit.generator.domain.service;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.mp.slow.toolkit.generator.application.IContentGenerator;

public abstract class AbstractGenerateVo2Dto implements IContentGenerator {

    @Override
    public void doGenerate(Project project, DataContext dataContext) {
        // 4. 织入代码 set->get
        this.weavingSetGetCode(project, dataContext);
    }

    protected void weavingSetGetCode(Project project, DataContext dataContext) {

    }

}
