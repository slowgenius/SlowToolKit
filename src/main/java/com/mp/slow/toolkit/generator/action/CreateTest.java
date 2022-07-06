package com.mp.slow.toolkit.generator.action;

import com.intellij.ide.actions.CreateClassAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElementFactory;

/**
 * @author hty
 * description: TODO
 * date: 2022/6/30
 */
public class CreateTest extends CreateClassAction {


    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        PsiElementFactory psiElementFactory = PsiElementFactory.getInstance(project);
        super.buildDialog(project, directory, builder);
    }
}
