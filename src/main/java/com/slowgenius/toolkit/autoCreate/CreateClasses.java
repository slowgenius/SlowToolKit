package com.slowgenius.toolkit.autoCreate;


import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import com.intellij.util.PlatformIcons;
import com.slowgenius.toolkit.base.VisibleConfig;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/6/29 20:50:08
 */

public class CreateClasses extends AnAction {

    private AnActionEvent anActionEvent;

    public CreateClasses() {
    }

    public CreateClasses(Icon icon) {
        super(PlatformIcons.CLASS_ICON);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        this.anActionEvent = anActionEvent;
        Project project = anActionEvent.getProject();
        DataContext dataContext = anActionEvent.getDataContext();
        IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (view != null) {
            final PsiDirectory dir = Objects.requireNonNull(view).getOrChooseDirectory();
            assert dir != null;
            assert project != null;
            CreateFileFromTemplateDialog.Builder builder = CreateFileFromTemplateDialog.createDialog(project);
            this.buildDialog(project, dir, builder);
            builder.show("Error", "class", new CreateFileFromTemplateDialog.FileCreator<>() {
                public PsiElement createFile(@NotNull String name, @NotNull String templateName) {
                    if (Character.isLowerCase(name.charAt(0))) {
                        return createPackage(dir, name);
                    }
                    List<String> collect;
                    if (name.contains(",")) {
                        collect = Arrays.stream(name.split(",")).collect(Collectors.toList());
                    } else {
                        collect = Collections.singletonList(name);
                    }
                    List<PsiClass> result = new ArrayList<>();
                    collect.forEach(className -> {
                        result.add(JavaDirectoryService.getInstance().createClass(dir, className, templateName, true));
                    });
                    //返回第一个元素
                    return result.get(0);
                }

                public boolean startInWriteAction() {
                    return false;
                }

                public @NotNull String getActionName(@NotNull String name, @NotNull String templateName) {
                    return "Create Something";
                }
            }, (createdElement) -> {
                if (createdElement != null) {
                    view.selectElement(createdElement);
                }
            });
        }

    }

    private List<PsiPackage> getAllSubPackage(List<PsiPackage> psiPackage) {

        List<PsiPackage> result = psiPackage.parallelStream()
                .flatMap(item -> {
                    List<PsiPackage> temp = new ArrayList<>();
                    ApplicationManager.getApplication().runReadAction(() -> {
                        if (item.getClasses().length != 0 || item.getSubPackages().length == 0) {
                            temp.add(item);
                        }
                        temp.addAll(Arrays.stream(item.getSubPackages()).collect(Collectors.toList()));
                    });
                    return temp.parallelStream();
                })
                .distinct()
                .collect(Collectors.toList());

        if (result.size() == psiPackage.size()) {
            return result;
        }
        return getAllSubPackage(result);
    }

    protected void buildDialog(@NotNull Project project, @NotNull PsiDirectory directory, CreateFileFromTemplateDialog.@NotNull Builder builder) {
        builder.setTitle("Intelligence Create");
        builder.addKind("Class", PlatformIcons.CLASS_ICON, "Class");
        Arrays.stream(FileTemplateManager.getInstance(project).getAllTemplates())
                .forEach(item -> {
                    if (item.getName().equals("JavaFXApplication")) {
                        return;
                    }
                    builder.addKind(item.getName(), null, item.getName());
                });
        builder.setValidator(new InputValidatorEx() {
            public String getErrorText(String inputString) {
                //非法字符提示信息
                return null;
            }

            public boolean checkInput(String inputString) {
                return true;
            }

            public boolean canClose(String inputString) {
                return !StringUtil.isEmptyOrSpaces(inputString) && this.getErrorText(inputString) == null;
            }
        });
    }

    private PsiDirectory createPackage(PsiDirectory directory, String packageName) {
        return directory.createSubdirectory(packageName);
    }


    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(VisibleConfig.INSTANCE.getProperties().get("create classes"));
    }
}
