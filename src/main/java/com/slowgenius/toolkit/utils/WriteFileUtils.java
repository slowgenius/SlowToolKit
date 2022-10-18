package com.slowgenius.toolkit.utils;

import com.intellij.codeInsight.actions.OptimizeImportsProcessor;
import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.slowgenius.toolkit.gen_entity.config.FreemarkerMybatisConfiguration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/19 21:20:23
 */

@SuppressWarnings("ALL")
public class WriteFileUtils {

    public static boolean writeByFreemarker(Project project, String templateName, Object param, String path, String fileName) {
        FreemarkerMybatisConfiguration freemarkerMybatisConfiguration = new FreemarkerMybatisConfiguration("/template/mybatis");
        Template template = null;
        try {
            template = freemarkerMybatisConfiguration.getTemplate(templateName);
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }

        StringWriter stringWriter = new StringWriter();
        try {
            assert template != null;
            template.process(param, stringWriter);
        } catch (TemplateException | IOException e) {
            System.out.println(e);
            return false;
        }
        boolean mkdirs = new File(path).mkdirs();
        VirtualFile virtualFile = null;
        try {
            virtualFile = Objects.requireNonNull(LocalFileSystem.getInstance().refreshAndFindFileByPath(path)).createChildData(project, fileName);
            virtualFile.setBinaryContent(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PsiFile file = PsiManager.getInstance(project).findFile(virtualFile);
        ReformatCodeProcessor reformatCodeProcessor = new ReformatCodeProcessor(project, file, null, false);
        reformatCodeProcessor.run();
        OptimizeImportsProcessor optimizeImportsProcessor = new OptimizeImportsProcessor(project, file);
        //JavaCodeStyleManager.getInstance(project).optimizeImports(Objects.requireNonNull(file));
        optimizeImportsProcessor.run();
        return true;
    }
}
