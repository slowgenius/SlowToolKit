package com.mp.tools.generator.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleNameLocationSettings;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.mp.tools.generator.domain.service.imp.MpProjectGeneratorImpl;
import com.mp.tools.generator.infrastructure.DataSetting;
import com.mp.tools.generator.infrastructure.ICONS;
import com.mp.tools.generator.infrastructure.MsgBundle;
import com.mp.tools.generator.ui.ProjectConfigUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

import static com.intellij.openapi.module.ModuleTypeId.JAVA_MODULE;

public class DDDModuleBuilder extends ModuleBuilder {

    private MpProjectGeneratorImpl projectGenerator = new MpProjectGeneratorImpl();

    @Override
    public Icon getNodeIcon() {
        return ICONS.SPRING_BOOT;
    }


    @Override
    protected @Nls(capitalization = Nls.Capitalization.Title) String getModuleTypeName() {
        return JAVA_MODULE;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getPresentableName() {
        return "Spring Boot";
    }

    @Override
    public String getDescription() {
        return MsgBundle.message("wizard.spring.boot.description");
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
        String artifactId = DataSetting.getInstance().getProjectConfig().get_artifactId();
        if (null != moduleNameLocationSettings && !StringUtil.isEmptyOrSpaces(artifactId)) {
            moduleNameLocationSettings.setModuleName(artifactId);
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
        rootModel.addContentEntry(virtualFile);

        Project project = rootModel.getProject();

        WriteCommandAction.Builder builder = WriteCommandAction.writeCommandAction(project);
        builder.run(() -> {
            projectGenerator.doGenerator(project, getContentEntryPath(), DataSetting.getInstance().getProjectConfig());
        });
    }


    @Override
    public ModuleType<?> getModuleType() {
        return ModuleTypeManager.getInstance().findByID(JAVA_MODULE);
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {

        // 添加工程配置步骤，可以自己定义需要的步骤，如果有多个可以依次添加
        DDDModuleConfigStep moduleConfigStep = new DDDModuleConfigStep(new ProjectConfigUI());

        return new ModuleWizardStep[]{moduleConfigStep};
    }
}
