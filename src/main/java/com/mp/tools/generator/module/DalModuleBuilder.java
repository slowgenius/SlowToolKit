//package com.mp.tools.generator.module;
//
//import com.intellij.ide.util.projectWizard.ModuleBuilder;
//import com.intellij.ide.util.projectWizard.ModuleNameLocationSettings;
//import com.intellij.ide.util.projectWizard.ModuleWizardStep;
//import com.intellij.ide.util.projectWizard.SettingsStep;
//import com.intellij.ide.util.projectWizard.WizardContext;
//import com.intellij.openapi.command.WriteCommandAction;
//import com.intellij.openapi.module.EmptyModuleType;
//import com.intellij.openapi.module.ModifiableModuleModel;
//import com.intellij.openapi.module.Module;
//import com.intellij.openapi.module.ModuleType;
//import com.intellij.openapi.module.ModuleWithNameAlreadyExists;
//import com.intellij.openapi.options.ConfigurationException;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.roots.ModifiableRootModel;
//import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
//import com.intellij.openapi.util.InvalidDataException;
//import com.intellij.openapi.util.io.FileUtil;
//import com.intellij.openapi.util.text.StringUtil;
//import com.intellij.openapi.vfs.LocalFileSystem;
//import com.intellij.openapi.vfs.VirtualFile;
//import com.mp.tools.generator.domain.service.IProjectGenerator;
//import com.mp.tools.generator.domain.service.imp.MpProjectGeneratorImpl;
//import com.mp.tools.generator.infrastructure.DataSetting;
//import com.mp.tools.generator.infrastructure.ICONS;
//import com.mp.tools.generator.infrastructure.MsgBundle;
//import com.mp.tools.generator.ui.ProjectConfigUI;
//import org.jdom.JDOMException;
//import org.jetbrains.annotations.Nls;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import javax.swing.*;
//import java.io.File;
//import java.io.IOException;
//import java.util.Objects;
//
///**
// * @author slowgenius
// * @version tools
// * @since 2022/6/22 22:38:15
// */
//
//public class DalModuleBuilder  extends ModuleBuilder {
//    private MpProjectGeneratorImpl projectGenerator = new MpProjectGeneratorImpl();
//
//    @Override
//    public Icon getNodeIcon() {
//        return ICONS.SPRING_BOOT;
//    }
//
//    @Override
//    public ModuleType<?> getModuleType() {
//        return new EmptyModuleType();
//    }
//
//    @Override
//    public @Nls(capitalization = Nls.Capitalization.Title)
//    String getPresentableName() {
//        return "Spring Boot";
//    }
//
//    @Override
//    public String getDescription() {
//        return MsgBundle.message("wizard.spring.boot.description");
//    }
//
//    /**
//     * 重写 builderId 挂载自定义模板
//     */
//    @Nullable
//    @Override
//    public String getBuilderId() {
//        return getClass().getName();
//    }
//
//    @Override
//    public @Nullable ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
//        ModuleNameLocationSettings moduleNameLocationSettings = settingsStep.getModuleNameLocationSettings();
//        String artifactId = DataSetting.getInstance().getProjectConfig().get_artifactId();
//        if (null != moduleNameLocationSettings && !StringUtil.isEmptyOrSpaces(artifactId)) {
//            moduleNameLocationSettings.setModuleName(artifactId);
//        }
//        return super.modifySettingsStep(settingsStep);
//    }
//
//    @Override
//    public @NotNull Module createModule(@NotNull ModifiableModuleModel moduleModel) throws InvalidDataException, IOException, ModuleWithNameAlreadyExists, JDOMException, ConfigurationException {
//
//        Project project = moduleModel.getProject();
//
//        WriteCommandAction.Builder builder = WriteCommandAction.writeCommandAction(project);
//        builder.run(() -> {
//            projectGenerator.generateDal(project, getContentEntryPath(), DataSetting.getInstance().getProjectConfig());
//        });
//
//        return super.createModule(moduleModel);
//    }
//
//
//    @Override
//    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
//
//        // 添加工程配置步骤，可以自己定义需要的步骤，如果有多个可以依次添加
//        DDDModuleConfigStep moduleConfigStep = new DDDModuleConfigStep(new ProjectConfigUI());
//
//        return new ModuleWizardStep[]{moduleConfigStep};
//    }
//}
