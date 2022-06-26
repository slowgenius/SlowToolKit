package com.mp.slow.toolkit.generator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiImportStatement;
import com.mp.slow.toolkit.utils.StrUtils;
import org.jetbrains.annotations.NotNull;

public class Vo2DtoGenerateAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PsiClass psiClass = (PsiClass) event.getDataContext().getData(CommonDataKeys.PSI_ELEMENT);
        Project project = event.getProject();
        assert project != null;
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiClass tableField1 = elementFactory.createAnnotationType("TableField");
        assert psiClass != null;
        WriteCommandAction.runWriteCommandAction(project, () -> {
            for (PsiField field : psiClass.getFields()) {
                PsiAnnotation anno;
                if (field.getName().equals("id")) {
                    anno = elementFactory.createAnnotationFromText("@TableId(\"id\")", tableField1);
                } else {
                    anno = elementFactory.createAnnotationFromText("@TableField(\"" + StrUtils.camelToUnderline(field.getName()) + "\")", tableField1);
                }
                field.addBefore(anno, field);
            }
            PsiImportStatement importStatementOnDemand = elementFactory.createImportStatementOnDemand("com.baomidou.mybatisplus.annotation");
            psiClass.addBefore(importStatementOnDemand, psiClass);

        });
    }

}
