package com.mp.slow.toolkit.generator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.mp.slow.toolkit.utils.StrUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MybatisPlusGenerateAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PsiElement data = Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).findElementAt(Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getCaretModel().getOffset());
        PsiClass psiClass = getPsiClass(data);
        if (psiClass == null) {
            return;
        }
        Project project = event.getProject();
        assert project != null;
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        WriteCommandAction.runWriteCommandAction(project, () -> {
            for (PsiField field : psiClass.getFields()) {
                String anno;
                if (field.getName().equals("id")) {
                    anno = "TableId(\"id\")";
                } else {
                    anno = "TableField(\"" + StrUtils.camelToUnderline(field.getName()) + "\")";
                }
                Objects.requireNonNull(field.getModifierList()).addAnnotation(anno);
            }
            PsiImportStatement importStatementOnDemand = elementFactory.createImportStatementOnDemand("com.baomidou.mybatisplus.annotation");
            psiClass.getParent().addBefore(importStatementOnDemand, psiClass);
            Document document = Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getDocument();
            PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);
            document.insertString(psiClass.getParent().getChildren()[1].getTextOffset(), "import com.baomidou.mybatisplus.annotation.TableId;\nimport com.baomidou.mybatisplus.annotation.TableField;");
            PsiDocumentManager.getInstance(project).commitAllDocuments();

            CodeStyleManager.getInstance(project).reformat(psiClass);
            JavaCodeStyleManager.getInstance(project).optimizeImports(Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)));

        });

    }

    private PsiClass getPsiClass(PsiElement data) {
        int count = 0;
        while (!(data instanceof PsiClass)) {
            assert data != null;
            data = data.getParent();
            count++;
            if (count > 10) {
                return null;
            }
        }
        return (PsiClass) data;
    }

}
