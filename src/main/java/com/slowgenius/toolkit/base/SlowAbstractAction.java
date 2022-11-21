package com.slowgenius.toolkit.base;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 项目所有action的父类
 *
 * @author hty
 * date: 2022/11/9
 */
public abstract class SlowAbstractAction extends AnAction {

    abstract public String getActionKey();

    @Override
    public void update(@NotNull AnActionEvent event) {
        Boolean enableAndVisible = VisibleConfig.getInstance().getProperties().getOrDefault(getActionKey(), null);
        if (Objects.nonNull(enableAndVisible)) {
            event.getPresentation().setEnabledAndVisible(enableAndVisible);
        }
    }
}
