package com.slowgenius.toolkit.genapidocjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.util.PsiUtil;
import com.slowgenius.toolkit.utils.SlowPsiUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/23 19:40:00
 */

public class GenApiDocJsonAction extends AnAction {

    private Project project;

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        project = event.getProject();
        PsiClass psiClass = SlowPsiUtils.getClassByEvent(event);
        List<PsiMethod> allApi = getAllApi(psiClass);
        List<JSONObject> collect = allApi.stream()
                .map(item -> assembleMethodJson(item)).collect(Collectors.toList());
        JSONArray jsonArray = new JSONArray(collect);
        JSONObject result = assembleBaseJson();
        result.put("paths", jsonArray);


    }


    private JSONObject assembleBaseJson() {
        JSONObject result = new JSONObject();
        result.put("openapi", "3.0.1");
        return result;
    }


    private JSONObject assembleMethodJson(PsiMethod psiMethod) {
        JSONObject result = new JSONObject();
        List<PsiParameter> paramList = Arrays.stream(psiMethod.getParameterList().getParameters()).collect(Collectors.toList());
        //设置parameters
        JSONArray paramArray = assembleParamJson(paramList);
        result.put("parameters", paramArray);
        //设置requestBody
        JSONObject bodyObject = assembleBodyJson(paramList);
        result.put("requestBody", bodyObject);
        return result;
    }


    private JSONArray assembleParamJson(List<PsiParameter> psiParameter) {

        JSONArray result = new JSONArray();

        return result;
    }

    /**
     * 通过参数列表获取到@ReuqestBody标注的对象，并转换成json
     */
    private JSONObject assembleBodyJson(List<PsiParameter> psiParameter) {
        if (psiParameter == null || psiParameter.isEmpty()) {
            return new JSONObject();
        }
        //获取requestBody注解标注的参数
        Optional<PsiParameter> requestBodyOpt = psiParameter.stream()
                .filter(item -> Arrays.stream(item.getAnnotations())
                        .anyMatch(psiAnnotation -> Optional.ofNullable(psiAnnotation.getQualifiedName()).orElse("").contains("RequestBody")))
                .findAny();
        if (requestBodyOpt.isEmpty()) {
            return null;
        }
        PsiParameter requestBodyParam = requestBodyOpt.get();
        JSONObject result = new JSONObject();
        result.put("type", getParamType(requestBodyParam));


        result.put("properties", buildBodyPropertiesJson(requestBodyParam));
        result.put("required", buildBodyRequiredJson(requestBodyParam));
        return buildBodyJson(result);
    }

    private JSONObject buildBodyPropertiesJson(PsiParameter psiParameter) {
        PsiClass psiClass = PsiUtil.resolveClassInType(psiParameter.getType());
        if (psiClass == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        Arrays.stream(psiClass.getAllFields()).forEach(item -> {
            JSONObject param = new JSONObject();
            param.put("description", item.getDocComment());
            param.put("type", "");
            param.put("", "");
            jsonObject.put(item.getName(), param);
        });
        return jsonObject;
    }

    private JSONArray buildBodyRequiredJson(PsiParameter psiParameter) {
        PsiClass psiClass = PsiUtil.resolveClassInType(psiParameter.getType());
        JSONArray jsonArray = new JSONArray();

        return jsonArray;
    }

    private String getParamType(PsiParameter psiParameter) {
        if (psiParameter.getType().getCanonicalText().contains("List") ||
                psiParameter.getType().getCanonicalText().contains("Collection") ||
                psiParameter.getType().getCanonicalText().contains("Set")) {
            return "array";
        }
        return "object";
    }


    /**
     * 传入schema信息，直接构建出requestBody节点
     */
    private JSONObject buildBodyJson(JSONObject schemaObject) {
        JSONObject result = new JSONObject();
        JSONObject contentObject = new JSONObject();
        JSONObject applicationObject = new JSONObject();
        applicationObject.put("schema", schemaObject);
        contentObject.put("application/json", applicationObject);
        result.put("content", contentObject);
        return result;
    }

    /**
     * 通过psiClass获取所有方法
     */
    private List<PsiMethod> getAllApi(PsiClass psiClass) {

        return null;
    }


    /**
     * 通过psiMethod 获取所有参数
     */
    private List<PsiField> getParams(PsiMethod psiMethod) {

        return null;
    }
}
