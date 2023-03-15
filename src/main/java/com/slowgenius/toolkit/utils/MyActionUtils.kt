package com.slowgenius.toolkit.utils

import com.intellij.database.psi.DbTable
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.psi.PsiElement
import java.util.*
import java.util.stream.Collectors

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/19 21:05:56
 */
object MyActionUtils {

    @JvmStatic
    fun getPsiElement(anActionEvent: AnActionEvent): PsiElement? {
        return anActionEvent.getData(LangDataKeys.PSI_ELEMENT)
    }

    @JvmStatic
    fun getDbTableList(anActionEvent: AnActionEvent): List<DbTable?> {
        return Arrays.stream(anActionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY))
            .map { item: PsiElement? -> item as DbTable? }
            .collect(Collectors.toList())
    }

    fun getDbTable(anActionEvent: AnActionEvent): DbTable? {
        return anActionEvent.getData(LangDataKeys.PSI_ELEMENT) as DbTable?
    }

    val defaultProject: Project
        get() = ProjectManager.getInstance().defaultProject
}