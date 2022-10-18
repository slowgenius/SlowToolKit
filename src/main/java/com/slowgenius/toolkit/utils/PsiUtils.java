package com.slowgenius.toolkit.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * @author hty
 * description: psi工具类
 * date: 2022/10/18
 */
public class PsiUtils {


    public static PsiClass getBu(Project project, String className) {
       return JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
    }
}
