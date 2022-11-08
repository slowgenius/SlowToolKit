package com.slowgenius.toolkit.gen.project.infrastructure;


import com.slowgenius.toolkit.gen.project.domain.model.ProjectConfigVO;

public class DataState {

   private ProjectConfigVO projectConfigVO = new ProjectConfigVO();

    public ProjectConfigVO getProjectConfigVO() {
        return projectConfigVO;
    }

    public void setProjectConfigVO(ProjectConfigVO projectConfigVO) {
        this.projectConfigVO = projectConfigVO;
    }

}
