package com.slowgenius.toolkit.test;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.vcs.actions.AnnotateToggleAction;
import org.jetbrains.annotations.NotNull;

/**
 * TODO
 *
 * @author hty
 * date: 2022/12/2
 */
public class ToggleTestAction extends ToggleAction {


    @Override
    public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
        return false;
    }

    @Override
    public void setSelected(@NotNull AnActionEvent anActionEvent, boolean b) {

    }

    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        Presentation presentation = e.getPresentation();
        presentation.setEnabled(true);
        presentation.setText("slowgenius");
    }
}
