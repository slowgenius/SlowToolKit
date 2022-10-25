package com.slowgenius.toolkit.genapidocjson;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/24 21:05:20
 */
public class GenApiDocContext {


    private Map<PsiMethod, PsiType> methodBodyMap = new HashMap<>();
    private List<PsiMethod> apiList;

    private final Project project;

    private PsiClass controller;

    private List<PsiType> bodyList = new ArrayList<>();

    private String basePath;


    public GenApiDocContext(Project project) {
        this.project = project;
    }


    public List<PsiMethod> getApiList() {
        return apiList;
    }

    public void setApiList(List<PsiMethod> apiList) {
        this.apiList = apiList;
    }

    public PsiClass getController() {
        return controller;
    }

    public void setController(PsiClass controller) {
        this.controller = controller;

        Optional<PsiAnnotation> mapping = Arrays.stream(controller.getAnnotations())
                .filter(item -> item.getText().contains("Mapping")).findAny();
        if (mapping.isEmpty()) {
            basePath = "";
            return;
        }
        basePath = mapping.get().getText().replaceAll("@\\w+Mapping", "")
                .replace("\"", "")
                .replace("(", "")
                .replace(")", "");
    }

    public List<PsiType> getBodyList() {
        return bodyList;
    }

    public void setBodyList(List<PsiType> bodyList) {
        this.bodyList = bodyList;
    }

    public String BasePath() {
        Optional<PsiAnnotation> mapping = Arrays.stream(controller.getAnnotations())
                .filter(item -> item.getText().contains("Mapping")).findAny();
        if (mapping.isEmpty()) {
            return "";
        }
        return mapping.get().getText().replaceAll("@\\w+Mapping", "")
                .replace("\"", "")
                .replace("(", "")
                .replace(")", "");
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Map<PsiMethod, PsiType> getMethodBodyMap() {
        return methodBodyMap;
    }

    public void setMethodBodyMap(Map<PsiMethod, PsiType> methodBodyMap) {
        this.methodBodyMap = methodBodyMap;
    }
}
