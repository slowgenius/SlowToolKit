package com.mp.tools.generator.infrastructure;


import com.mp.tools.generator.domain.model.vo.ProjectConfigVO;

public class DataState {

   private ProjectConfigVO projectConfigVO = new ProjectConfigVO();

    public ProjectConfigVO getProjectConfigVO() {
        return projectConfigVO;
    }

    public void setProjectConfigVO(ProjectConfigVO projectConfigVO) {
        this.projectConfigVO = projectConfigVO;
    }

}
