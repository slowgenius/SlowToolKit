package com.slowgenius.toolkit.base

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 项目所有action的父类
 *
 * @author hty
 * date: 2022/11/9
 */
abstract class SlowAbstractAction : AnAction() {

    /**
     * 统一抽象方法
     */
    abstract fun getActionKey(): String

    override fun update(event: AnActionEvent) {
        val enableAndVisible = VisibleConfig.instance.properties[getActionKey()]
        if (enableAndVisible != null) {
            event.presentation.isEnabledAndVisible = enableAndVisible
        }
    }
}