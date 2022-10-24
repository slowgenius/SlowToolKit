package com.slowgenius.toolkit.genapidocjson;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/24 21:05:20
 */
public class GenApiDocContext {


    private List<PsiMethod> apiList;

    private final Project project;

    private PsiClass javaClass;

    private List<PsiClass> bodyList = new ArrayList<>();

    public GenApiDocContext(Project project) {
        this.project = project;
    }


    public List<PsiMethod> getApiList() {
        return apiList;
    }

    public void setApiList(List<PsiMethod> apiList) {
        this.apiList = apiList;
    }

    public PsiClass getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(PsiClass javaClass) {
        this.javaClass = javaClass;
    }

    public List<PsiClass> getBodyList() {
        return bodyList;
    }

    public void setBodyList(List<PsiClass> bodyList) {
        this.bodyList = bodyList;
    }
}
