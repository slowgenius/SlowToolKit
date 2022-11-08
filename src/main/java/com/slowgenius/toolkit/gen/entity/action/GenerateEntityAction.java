package com.slowgenius.toolkit.gen.entity.action;

import com.intellij.database.psi.DbTable;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import com.intellij.util.SystemProperties;
import com.slowgenius.toolkit.gen.entity.pojo.TemplateInfo;
import com.slowgenius.toolkit.utils.DbInfoUtils;
import com.slowgenius.toolkit.utils.MyActionUtils;
import com.slowgenius.toolkit.utils.SlowStrUtils;
import com.slowgenius.toolkit.utils.TypeConverterUtils;
import com.slowgenius.toolkit.utils.WriteFileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaSourceRootType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/17 18:06:15
 */
public class GenerateEntityAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        //新建一个包选择器，并显示
        PackageChooserDialog packageChooserDialog = new PackageChooserDialog("Package Chooser", event.getProject());
        packageChooserDialog.show();

        PsiPackage selectedPackage = packageChooserDialog.getSelectedPackage();
        List<DbTable> selectDbTableList = Optional.ofNullable(MyActionUtils.getDbTableList(event)).orElseThrow(RuntimeException::new);

        selectDbTableList.forEach(selectDbTable -> {
            TemplateInfo templateInfo = new TemplateInfo();
            List<TemplateInfo.FieldInfo> fieldInfoList = DbInfoUtils.getDasColumnList(selectDbTable)
                    .stream()
                    .map(column -> new TemplateInfo.FieldInfo(SlowStrUtils.underlineToCamel(column.getName()), TypeConverterUtils.convert(column.getDataType().getSpecification()), column.getComment()))
                    .collect(Collectors.toList());

            templateInfo.setFieldInfoList(fieldInfoList);

            TemplateInfo.ClassInfo classInfo = new TemplateInfo.ClassInfo(SlowStrUtils.underlineToCamel(selectDbTable.getName()), selectDbTable.getComment());
            templateInfo.setClassInfo(classInfo);
            templateInfo.setPackageInfo(selectedPackage.getQualifiedName());
            templateInfo.setSince(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss")));
            templateInfo.setAuthor(SystemProperties.getUserName());

            String path = FileUtil.toSystemIndependentName(selectedPackage.getDirectories()[0].getVirtualFile().getPresentableUrl());
            ApplicationManager.getApplication().runWriteAction(() -> {
                WriteFileUtils.writeByFreemarker(event.getProject(), "genEntity.ftl", templateInfo, path, templateInfo.getClassInfo().getName() + ".java");
            });

        });

    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiElement psiElement = MyActionUtils.getPsiElement(e);
        e.getPresentation().setEnabledAndVisible(psiElement instanceof DbTable);
    }


    public Module findModuleByName(Project project) {
        return ModuleManager.getInstance(project).findModuleByName("name");
    }

    public PsiDirectory findPossiblePackageDirectoryInModule(Module module, String packageName) {
        return PackageUtil.findPossiblePackageDirectoryInModule(module, packageName);
    }

    public List<VirtualFile> getFileList(Module module) {
        return ModuleRootManager.getInstance(module).getSourceRoots(JavaSourceRootType.SOURCE);
    }

    public ModuleManager getModuleManager(Project project) {
        return ModuleManager.getInstance(project);
    }

}
