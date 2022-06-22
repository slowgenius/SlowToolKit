package com.mp.tools.generator.factory;


import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import com.mp.tools.generator.infrastructure.ICONS;
import com.mp.tools.generator.module.DDDModuleBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TemplateFactory extends ProjectTemplatesFactory {

    @NotNull
    @Override
    public String[] getGroups() {
        return new String[]{"中台脚手架"};
    }

    @Override
    public Icon getGroupIcon(String group) {
        return ICONS.DDD;
    }

    @NotNull
    @Override
    public ProjectTemplate[] createTemplates(@Nullable String group, WizardContext context) {
        ProjectTemplate[] projectTemplates = {new BuilderBasedTemplate(new DDDModuleBuilder())};
        return projectTemplates;
    }

}
