package com.slowgenius.toolkit.gen_entity.action;

import com.intellij.database.Dbms;
import com.intellij.database.model.DasColumn;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.JBIterable;
import com.slowgenius.toolkit.gen_entity.config.FreemarkerMybatisConfiguration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/17 18:06:15
 */
public class GenerateEntityAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        if (!(psiElement instanceof DbTable)) {
            return;
        }
        DbTable selectDbTable = (DbTable) psiElement;

        Dbms dbms = selectDbTable.getDbms();
        PsiElement[] children = selectDbTable.getChildren();

        Map<String, Object> params = new HashMap<>();
        JBIterable<? extends DasColumn> columns = DasUtil.getColumns(selectDbTable);
        List<FieldInfo> list = new ArrayList<>();
        for (DasColumn column : columns) {
            FieldInfo fieldInfo = new FieldInfo(column.getName(), column.getDataType().getSpecification());
            list.add(fieldInfo);
        }
        params.put("fieldList", list);
        FreemarkerMybatisConfiguration freemarkerMybatisConfiguration = new FreemarkerMybatisConfiguration();
        Template template = null;
        try {
            template = freemarkerMybatisConfiguration.getTemplate("mybatis.ftl");
        } catch (IOException e) {
            System.out.println("====");
        }

        StringWriter stringWriter = new StringWriter();
        try {
            template.process(params, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }

        String basePath = event.getProject().getBasePath();
        String path = FileUtil.toSystemIndependentName(basePath + "/src/main/java/com/slowgenius/entity");
        new File(path).mkdirs();
        VirtualFile virtualFile = null;
        try {
            virtualFile = Objects.requireNonNull(LocalFileSystem.getInstance().refreshAndFindFileByPath(path)).createChildData(event.getProject(), "Test.java");
            virtualFile.setBinaryContent(stringWriter.toString().getBytes("UTF-8"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class FieldInfo {

        private String name;

        private String type;

        public FieldInfo(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
