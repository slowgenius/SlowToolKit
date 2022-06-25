package com.mp.slow.toolkit.generator.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.mp.slow.toolkit.generator.domain.model.ProjectConfigVO;
import com.mp.slow.toolkit.generator.infrastructure.DataSetting;
import com.mp.slow.toolkit.generator.ui.ProjectApiConfigUI;

import javax.swing.*;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/24 20:40:46
 */

public class ApiModuleConfigStep extends ModuleWizardStep {


    private final ProjectApiConfigUI projectApiConfigUI;

    public ApiModuleConfigStep(ProjectApiConfigUI projectApiConfigUI) {
        this.projectApiConfigUI = projectApiConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return projectApiConfigUI.getComponent();
    }

    @Override
    public void updateDataModel() {

    }

    @Override
    public boolean validate() throws ConfigurationException {
        // 获取配置信息，写入到 DataSetting
        ProjectConfigVO projectConfig = DataSetting.getInstance().getProjectConfig();
        projectConfig.setProjectName("mp-api-" + projectApiConfigUI.getProjectName().getText());
        projectConfig.setBasePackage("mp.lylb.api." + projectApiConfigUI.getProjectName().getText());
        return super.validate();
    }
}
