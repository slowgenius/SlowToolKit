package com.mp.slow.toolkit.generator.domain.model;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import lombok.Data;

@Data
public class GenerateContext {

    /**
     * 工程对象
     */
    private Project project;
    /**
     * 文件
     */
    private PsiFile psiFile;
    /**
     * 数据上下文
     */
    private DataContext dataContext;
    /**
     * 编辑器
     */
    private Editor editor;
    /**
     * 元素
     */
    private PsiElement psiElement;
    /**
     * 位点
     */
    private Integer offset;
    /**
     * 文档
     */
    private Document document;
    /**
     * 行号
     */
    private Integer lineNumber;
    /**
     * 开始位置
     */
    private Integer startOffset;
    /**
     * 文本编辑
     */
    private CharSequence editorText;
}
