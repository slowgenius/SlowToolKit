package com.mp.tools.generator.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.mp.tools.generator.domain.model.vo.ProjectConfigVO;
import com.mp.tools.generator.infrastructure.DataSetting;
import com.mp.tools.generator.ui.ProjectConfigUI;

import javax.swing.*;

public class DDDModuleConfigStep extends ModuleWizardStep {

    private ProjectConfigUI projectConfigUI;

    public DDDModuleConfigStep(ProjectConfigUI projectConfigUI) {
        this.projectConfigUI = projectConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return projectConfigUI.getComponent();
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        // 获取配置信息，写入到 DataSetting
        ProjectConfigVO projectConfig = DataSetting.getInstance().getProjectConfig();
        projectConfig.setProjectName(projectConfigUI.getProjectName().getText());
        projectConfig.setBasePackage("mp.lylb.serv." + projectConfig.getProjectName().replace("mp-serv-", ""));
        return super.validate();
    }

}
