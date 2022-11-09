package com.slowgenius.toolkit.base;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 项目所有action的父类
 *
 * @author hty
 * date: 2022/11/9
 */
public abstract class AbstractAction extends AnAction {

    public static VisibleConfig visibleConfig;



    @Override
    public void update(@NotNull AnActionEvent event) {


    }
}
