package com.mp.slow.toolkit.generator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author hty
 * description: TODO
 * date: 2022/6/30
 */
public class CreateTest extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PsiElement data = Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).findElementAt(Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getCaretModel().getOffset());
        Project project = event.getProject();
        PsiClass psiClass = null;
        for (PsiElement child : Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).getChildren()) {
            if (child instanceof PsiClass) {
                psiClass = (PsiClass) child;
                break;
            }
        }
        PsiElement replace = data.getPrevSibling().getPrevSibling().getFirstChild();
        PsiClass finalPsiClass = psiClass;
        WriteCommandAction.runWriteCommandAction(project, () -> {
            Document document = Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getDocument();
            assert project != null;
            PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);
            document.replaceString(replace.getTextOffset(), replace.getTextOffset() + replace.getTextLength(), "Class a = new Class();a =Integer.class; ");
            PsiDocumentManager.getInstance(project).commitAllDocuments();
            CodeStyleManager.getInstance(project).reformat(finalPsiClass);
        });

        
    }
}
