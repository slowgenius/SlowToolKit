package com.slowgenius.toolkit.generator.infrastructure;


import com.slowgenius.toolkit.generator.domain.model.ProjectConfigVO;

public class DataState {

   private ProjectConfigVO projectConfigVO = new ProjectConfigVO();

    public ProjectConfigVO getProjectConfigVO() {
        return projectConfigVO;
    }

    public void setProjectConfigVO(ProjectConfigVO projectConfigVO) {
        this.projectConfigVO = projectConfigVO;
    }

}
