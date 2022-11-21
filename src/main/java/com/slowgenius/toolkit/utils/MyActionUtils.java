package com.slowgenius.toolkit.utils;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/19 21:05:56
 */

public class MyActionUtils {


    public static PsiElement getPsiElement(AnActionEvent anActionEvent) {
        return anActionEvent.getData(LangDataKeys.PSI_ELEMENT);
    }


    public static List<DbTable> getDbTableList(AnActionEvent anActionEvent) {
        return Arrays.stream(anActionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY)).map(item -> (DbTable) item).collect(Collectors.toList());
    }

    public static DbTable getDbTable(AnActionEvent anActionEvent) {
        return (DbTable) anActionEvent.getData(LangDataKeys.PSI_ELEMENT);
    }


    public static Project getDefaultProject() {
        return ProjectManager.getInstance().getDefaultProject();
    }
}
