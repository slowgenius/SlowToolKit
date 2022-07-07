package com.mp.slow.toolkit.generator.action;


import com.intellij.codeInsight.daemon.JavaErrorBundle;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightClassUtil;
import com.intellij.core.JavaPsiBundle;
import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.CreateFromTemplateHandler;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.ide.fileTemplates.JavaCreateFromTemplateHandler;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.java.JavaBundle;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameHelper;
import com.intellij.psi.PsiPackage;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/6/29 20:50:08
 */

public class CreateClasses extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
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
                    PsiPackage choosePackage = JavaDirectoryService.getInstance().getPackage(dir);
                    assert choosePackage != null;

                    PsiPackage parentPackage = choosePackage.getParentPackage();
                    if (parentPackage == null) {
                        return JavaDirectoryService.getInstance().createClass(choosePackage.getDirectories()[0], name, templateName, true);
                    }
                    List<PsiClass> result = new ArrayList<>();

                    List<PsiPackage> allSubPackage = getAllSubPackage(Arrays.stream(Objects.requireNonNull(parentPackage.getParentPackage()).getSubPackages()).collect(Collectors.toList()));
                    allSubPackage.forEach(psiPackage -> {
                        if (psiPackage.getQualifiedName().equals(choosePackage.getQualifiedName())) {
                            result.add(JavaDirectoryService.getInstance().createClass(psiPackage.getDirectories()[0], name, templateName, true));
                            return;
                        }
                        Arrays.stream(FileTemplateManager.getInstance(project).getAllTemplates())
                                .forEach(item -> {
                                    if (psiPackage.getQualifiedName().endsWith(item.getName())) {
                                        for (PsiDirectory directory : psiPackage.getDirectories()) {
                                            JavaDirectoryService.getInstance().createClass(directory, name, item.getName(), true);
                                        }
                                    }
                                });
                    });
                    return result.get(0);
                }

                public boolean startInWriteAction() {
                    return false;
                }

                public @NotNull String getActionName(@NotNull String name, @NotNull String templateName) {

                    return "Create Classes";
                }
            }, (createdElement) -> {
                if (createdElement != null) {
                    view.selectElement(createdElement);
                }
            });

            PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(dir);
            assert aPackage != null;
            PsiPackage[] subPackages = Objects.requireNonNull(aPackage.getParentPackage()).getSubPackages();
            for (PsiPackage subPackage : subPackages) {
                System.out.println(subPackage.getDirectories()[0].getName());
                //JavaDirectoryService.getInstance().createClass(dir, className, templateName, true);
            }
        }

    }

    private List<PsiPackage> getAllSubPackage(List<PsiPackage> psiPackage) {

        List<PsiPackage> result = psiPackage.parallelStream()
                .flatMap(item -> {
                    List<PsiPackage> temp = new ArrayList<>();
                    ApplicationManager.getApplication().runReadAction(()->{
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
        builder.setTitle(JavaBundle.message("action.create.new.class", new Object[0])).addKind(JavaPsiBundle.message("node.class.tooltip", new Object[0]), PlatformIcons.CLASS_ICON, "Class");

        PsiDirectory[] dirs = new PsiDirectory[]{directory};
        FileTemplate[] fileTemplates = FileTemplateManager.getInstance(project).getAllTemplates();
        int length = fileTemplates.length;

        for (int i = 0; i < length; ++i) {
            FileTemplate template = fileTemplates[i];
            CreateFromTemplateHandler handler = FileTemplateUtil.findHandler(template);
            if (handler instanceof JavaCreateFromTemplateHandler && handler.handlesTemplate(template) && handler.canCreate(dirs)) {
                builder.addKind(template.getName(), JavaFileType.INSTANCE.getIcon(), template.getName());
            }
        }

        builder.setValidator(new InputValidatorEx() {
            public String getErrorText(String inputString) {
                if (inputString.length() > 0 && !PsiNameHelper.getInstance(project).isQualifiedName(inputString)) {
                    return JavaErrorBundle.message("create.class.action.this.not.valid.java.qualified.name", new Object[0]);
                } else {
                    String shortName = StringUtil.getShortName(inputString);
                    return HighlightClassUtil.isRestrictedIdentifier(shortName, LanguageLevel.HIGHEST) ? JavaErrorBundle.message("restricted.identifier", new Object[]{shortName}) : null;
                }
            }

            public boolean checkInput(String inputString) {
                return true;
            }

            public boolean canClose(String inputString) {
                return !StringUtil.isEmptyOrSpaces(inputString) && this.getErrorText(inputString) == null;
            }
        });
    }


    public void test(AnActionEvent anActionEvent) {

    }
}
