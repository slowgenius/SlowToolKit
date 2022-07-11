package com.slowgenius.toolkit.generator.action;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/11 21:49:14
 */

public class RequestParamIntention extends PsiElementBaseIntentionAction implements IntentionAction {

    @NotNull
    public String getText() {
        return "Generate @RequestParam on params";
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        PsiMethod psiMethod = (PsiMethod) psiElement.getParent();
        PsiParameterList parameterList = psiMethod.getParameterList();
        if (parameterList.isEmpty()) {
            return;
        }
        ApplicationManager.getApplication().runWriteAction(() -> {
            for (PsiParameter parameter : parameterList.getParameters()) {
                Optional<PsiAnnotation> find = Arrays.stream(Objects.requireNonNull(parameter.getModifierList()).getAnnotations())
                        .filter(item -> Objects.requireNonNull(item.getQualifiedName()).contains("RequestParam"))
                        .findFirst();
                if (find.isEmpty()) {
                    Objects.requireNonNull(parameter.getModifierList()).addAnnotation("RequestParam(value = \"" + parameter.getName() + "\")");
                }
            }

            PsiClass psiClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass.class);
            Document document = editor.getDocument();
            PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);
            assert psiClass != null;
            document.insertString(psiClass.getParent().getChildren()[1].getTextOffset(), "\nimport org.springframework.web.bind.annotation.RequestParam;");
            PsiDocumentManager.getInstance(project).commitAllDocuments();

            CodeStyleManager.getInstance(project).reformat(psiClass);
            JavaCodeStyleManager.getInstance(project).optimizeImports(psiClass.getContainingFile());
        });


    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        return psiElement instanceof PsiIdentifier && psiElement.getParent() instanceof PsiMethod;
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return "RequestParamIntention";
    }
}
