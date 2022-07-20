package com.slowgenius.toolkit.gen_entity.action;

import com.intellij.database.psi.DbTable;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import com.slowgenius.toolkit.gen_entity.pojo.TemplateInfo;
import com.slowgenius.toolkit.utils.DbInfoUtil;
import com.slowgenius.toolkit.utils.MyActionUtil;
import com.slowgenius.toolkit.utils.StrUtils;
import com.slowgenius.toolkit.utils.TypeConverter;
import com.slowgenius.toolkit.utils.WriteFileUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/17 18:06:15
 */
public class GenerateEntityAction extends AnAction {

    PackageChooserDialog packageChooserDialog = null;


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            if (packageChooserDialog == null) {
                packageChooserDialog = new PackageChooserDialog("Package Chooser", event.getProject());
            }
            //packageChooserDialog.selectPackage("com.slowgenius.entity");
        } catch (Exception ignored) {
        }
        packageChooserDialog.show();


//        ModuleManager.getInstance(event.getProject()).findModuleByName("name");
//
//        ModuleManager moduleManager = ModuleManager.getInstance(event.getProject());
//        Module[] modules = moduleManager.getModules();
//
//
//        List<VirtualFile> virtualFileList = ModuleRootManager.getInstance(module).getSourceRoots(JavaSourceRootType.SOURCE);

        PsiPackage selectedPackage = packageChooserDialog.getSelectedPackage();
        List<DbTable> selectDbTableList = Optional.ofNullable(MyActionUtil.getDbTableList(event)).orElseThrow(RuntimeException::new);

        selectDbTableList.forEach(selectDbTable -> {
            TemplateInfo templateInfo = new TemplateInfo();
            List<TemplateInfo.FieldInfo> fieldInfoList = DbInfoUtil.getDasColumnList(selectDbTable)
                    .stream()
                    .map(column -> new TemplateInfo.FieldInfo(StrUtils.underlineToCamel(column.getName()), TypeConverter.convert(column.getDataType().getSpecification()), column.getComment()))
                    .collect(Collectors.toList());

            templateInfo.setFieldInfoList(fieldInfoList);

            TemplateInfo.ClassInfo classInfo = new TemplateInfo.ClassInfo(StrUtils.underlineToCamel(selectDbTable.getName()), selectDbTable.getComment());
            templateInfo.setClassInfo(classInfo);
            templateInfo.setPackageInfo(selectedPackage.getQualifiedName());


            String path = FileUtil.toSystemIndependentName(selectedPackage.getDirectories()[0].getVirtualFile().getPresentableUrl());
            ApplicationManager.getApplication().runWriteAction(() -> {
                WriteFileUtil.writeByFreemarker(event.getProject(), "genEntity.ftl", templateInfo, path, templateInfo.getClassInfo().getName() + ".java");
            });

        });

    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiElement psiElement = MyActionUtil.getPsiElement(e);
        e.getPresentation().setEnabledAndVisible(psiElement instanceof DbTable);
    }
}