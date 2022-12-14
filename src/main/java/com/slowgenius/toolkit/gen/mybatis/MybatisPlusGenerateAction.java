package com.slowgenius.toolkit.gen.mybatis;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.slowgenius.toolkit.utils.SlowStrUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 遇到的问题
 * <p>
 * 1. 导包的问题
 * 通过document的insertString方法直接输入文本，然后用JavaCodeStyleManager.optimizeImports()方法格式化
 * 要注意Document的事务问题，需要先开启事务才能编辑
 * <p>
 * 2. 给字段添加注解
 * 通过元素节点的方式总是缺少空格啥的，不知道为啥不成功，用getModifierList的addAnnotation方法可以直接添加，倒是省事。。
 */
public class MybatisPlusGenerateAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PsiElement data = Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).findElementAt(Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getCaretModel().getOffset());
        PsiClass psiClass = recursionGetPsiElement(data);
        if (psiClass == null) {
            return;
        }
        Project project = event.getProject();
        assert project != null;
        //执行写命令
        WriteCommandAction.runWriteCommandAction(project, () -> {
            //添加竹节
            for (PsiField field : psiClass.getFields()) {
                String anno = field.getName().equals("id") ? "TableId(\"id\")" : "TableField(\"" + SlowStrUtils.camelToUnderline(field.getName()) + "\")";
                Objects.requireNonNull(field.getModifierList()).addAnnotation(anno);
            }
            //添加import
            Document document = Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getDocument();
            PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);
            document.insertString(psiClass.getParent().getChildren()[1].getTextOffset(), "import com.baomidou.mybatisplus.annotation.TableId;\nimport com.baomidou.mybatisplus.annotation.TableField;");
            //提交
            PsiDocumentManager.getInstance(project).commitAllDocuments();
            //格式化代码
            CodeStyleManager.getInstance(project).reformat(psiClass);
            JavaCodeStyleManager.getInstance(project).optimizeImports(Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)));
        });
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        PsiElement data = Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).findElementAt(Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getCaretModel().getOffset());
        PsiClass psiClass = recursionGetPsiElement(data);
        if (psiClass == null) {
            event.getPresentation().setEnabledAndVisible(false);
            return;
        }
        event.getPresentation().setEnabledAndVisible(true);
    }

    public PsiClass recursionGetPsiElement(PsiElement data) {
        int count = 0;
        while (!(data instanceof PsiClass)) {
            assert data != null;
            data = data.getParent();
            count++;
            if (count > 5) {
                return null;
            }
        }
        return (PsiClass) data;
    }
}
