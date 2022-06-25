package com.mp.slow.toolkit.generator.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.mp.slow.toolkit.generator.domain.model.ProjectConfigVO;
import com.mp.slow.toolkit.generator.infrastructure.DataSetting;
import com.mp.slow.toolkit.generator.ui.ProjectServerConfigUI;

import javax.swing.*;

public class ServerModuleConfigStep extends ModuleWizardStep {

    private final ProjectServerConfigUI projectServerConfigUI;

    public ServerModuleConfigStep(ProjectServerConfigUI projectServerConfigUI) {
        this.projectServerConfigUI = projectServerConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return projectServerConfigUI.getComponent();
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        // 获取配置信息，写入到 DataSetting
        ProjectConfigVO projectConfig = DataSetting.getInstance().getProjectConfig();
        projectConfig.setProjectName("mp-serv-" + projectServerConfigUI.getProjectName().getText());
        projectConfig.setBasePackage("mp.lylb.serv." + projectServerConfigUI.getProjectName().getText());
        return super.validate();
    }

}
