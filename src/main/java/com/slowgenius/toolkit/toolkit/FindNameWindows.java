package com.slowgenius.toolkit.toolkit;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.slowgenius.toolkit.toolkit.ui.TranslatorUI;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/21 20:58:02
 */

public class FindNameWindows extends SimpleToolWindowPanel {

    private Project project;
    private TranslatorUI translatorUI;

    public FindNameWindows(Project project) {
        super(false, true);
        this.project = project;
        translatorUI = new TranslatorUI(project);

        // 设置窗体侧边栏按钮
        DefaultActionGroup group = new DefaultActionGroup();

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("bar", group, false);
        toolbar.setTargetComponent(this);
        setToolbar(toolbar.getComponent());
    }
}
