package com.slowgenius.toolkit.gen.project.factory;


import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import com.slowgenius.toolkit.gen.project.infrastructure.ICONS;
import com.slowgenius.toolkit.gen.project.module.ApiModuleBuilder;
import com.slowgenius.toolkit.gen.project.module.ServerModuleBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TemplateFactory extends ProjectTemplatesFactory {

    @NotNull
    @Override
    public String @NotNull [] getGroups() {
        return new String[]{"中台脚手架"};
    }

    @Override
    public Icon getGroupIcon(String group) {
        return ICONS.DDD;
    }

    @NotNull
    @Override
    public ProjectTemplate @NotNull [] createTemplates(@Nullable String group, WizardContext context) {
        return new ProjectTemplate[]{new BuilderBasedTemplate(new ApiModuleBuilder()), new BuilderBasedTemplate(new ServerModuleBuilder())};
    }

}
