package com.mp.slow.toolkit.generator.module;

import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleNameLocationSettings;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.mp.slow.toolkit.generator.domain.service.imp.project.MpApiProjectGeneratorImpl;
import com.mp.slow.toolkit.generator.infrastructure.DataSetting;
import com.mp.slow.toolkit.generator.infrastructure.ICONS;
import com.mp.slow.toolkit.generator.ui.ProjectApiConfigUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

import static com.intellij.openapi.module.ModuleTypeId.JAVA_MODULE;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/24 20:33:41
 */

public class ApiModuleBuilder extends JavaModuleBuilder {

    private final MpApiProjectGeneratorImpl projectGenerator = new MpApiProjectGeneratorImpl();

    @Override
    public Icon getNodeIcon() {
        return ICONS.API_ICON;
    }

    @Override
    protected @Nls(capitalization = Nls.Capitalization.Title) String getModuleTypeName() {
        return JAVA_MODULE;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getPresentableName() {
        return "Api项目";
    }

    @Override
    public String getDescription() {
        return "Api项目脚手架";
    }

    /**
     * 重写 builderId 挂载自定义模板
     */
    @Nullable
    @Override
    public String getBuilderId() {
        return getClass().getName();
    }

    @Override
    public @Nullable ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        ModuleNameLocationSettings moduleNameLocationSettings = settingsStep.getModuleNameLocationSettings();
        String project = DataSetting.getInstance().getProjectConfig().getProjectName();
        if (null != moduleNameLocationSettings && !StringUtil.isEmptyOrSpaces(project)) {
            moduleNameLocationSettings.setModuleName(project);
        }
        return super.modifySettingsStep(settingsStep);
    }

    @Override
    public void setupRootModel(@NotNull ModifiableRootModel rootModel) {
        // 设置 JDK
        if (null != this.myJdk) {
            rootModel.setSdk(this.myJdk);
        } else {
            rootModel.inheritSdk();
        }
        // 生成工程路径
        String path = FileUtil.toSystemIndependentName(Objects.requireNonNull(getContentEntryPath()));
        new File(path).mkdirs();
        VirtualFile virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
        assert virtualFile != null;
        rootModel.addContentEntry(virtualFile);

        Project project = rootModel.getProject();

        WriteCommandAction.Builder builder = WriteCommandAction.writeCommandAction(project);
        builder.run(() -> {
            projectGenerator.doGenerator(project, getContentEntryPath(), DataSetting.getInstance().getProjectConfig());
        });
    }


    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        // 添加工程配置步骤，可以自己定义需要的步骤，如果有多个可以依次添加
        ProjectApiConfigUI projectServerConfigUI = new ProjectApiConfigUI();
        ApiModuleConfigStep moduleConfigStep = new ApiModuleConfigStep(projectServerConfigUI);
        wizardContext.setProjectName(projectServerConfigUI.getProjectName().getName());
        return new ModuleWizardStep[]{moduleConfigStep};
    }
}
