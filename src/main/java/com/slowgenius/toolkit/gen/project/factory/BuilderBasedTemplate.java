package com.slowgenius.toolkit.gen.project.factory;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.platform.ProjectTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Dmitry Avdeev
 */
public class BuilderBasedTemplate implements ProjectTemplate {
    private final ModuleBuilder myBuilder;

    public BuilderBasedTemplate(ModuleBuilder builder) {
        myBuilder = builder;
    }

    @NotNull
    @Override
    public String getName() {
        return myBuilder.getPresentableName();
    }

    @Nullable
    @Override
    public String getDescription() {
        return myBuilder.getDescription();
    }

    @Override
    public Icon getIcon() {
        return myBuilder.getNodeIcon();
    }

    @NotNull
    @Override
    public ModuleBuilder createModuleBuilder() {
        return myBuilder;
    }

    @Override
    public @Nullable ValidationInfo validateSettings() {
        return null;
    }


    @Override
    public String toString() {
        return getName();
    }
}