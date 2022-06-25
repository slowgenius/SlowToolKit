package com.mp.slow.toolkit.generator.domain.service;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.mp.slow.toolkit.generator.application.IProjectGenerator;
import com.mp.slow.toolkit.generator.domain.model.ProjectConfigVO;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public abstract class AbstractProjectGenerator extends FreemarkerConfiguration implements IProjectGenerator {

    @Override
    public abstract void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);


    public void writeFile(Project project, String packageName, String entryPath, String name, String ftl, Object dataModel) {
        VirtualFile virtualFile;
        try {
            virtualFile = createPackageDir(packageName, entryPath).createChildData(project, name);
            StringWriter stringWriter = new StringWriter();
            Template template = super.getTemplate(ftl);
            template.process(dataModel, stringWriter);
            virtualFile.setBinaryContent(stringWriter.toString().getBytes("UTF-8"));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public static VirtualFile createPackageDir(String packageName, String entryPath) {
        String path = FileUtil.toSystemIndependentName(entryPath + "/" + StringUtil.replace(packageName, ".", "/"));
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }

}
