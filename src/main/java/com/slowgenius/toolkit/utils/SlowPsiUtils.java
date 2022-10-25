package com.slowgenius.toolkit.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hty
 * description: psi工具类
 * date: 2022/10/18
 */
public class SlowPsiUtils {


    public static PsiClass getClassByName(Project project, String className) {
        return JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
    }

    public static PsiClass getClassByEvent(AnActionEvent event) {
        PsiElement data = Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).findElementAt(Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getCaretModel().getOffset());
        assert data != null;
        return (PsiClass) data.getParent();
    }

    public static String parseComment(PsiDocComment docComment) {
        return Optional.ofNullable(docComment).map(PsiElement::getText).map(SlowStrUtils::getComment).orElse("");
    }


    public static List<PsiField> getFields(PsiClass psiClass) {
        Map<String, PsiField> collect = Arrays.stream(psiClass.getFields()).collect(Collectors.toMap(PsiField::getName, Function.identity()));
        return Arrays.stream(Objects.requireNonNull(psiClass).getMethods())
                .filter(Objects::nonNull)
                .filter(item -> item.getName().startsWith("get"))
                .map(item -> collect.getOrDefault(SlowStrUtils.firstLetterLower(item.getName().replace("get", "")), null)).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public static PsiClass getClassByTYpe(PsiType psiType) {
        return PsiUtil.resolveClassInType(psiType);
    }
}
