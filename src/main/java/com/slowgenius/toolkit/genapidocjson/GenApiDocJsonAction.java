package com.slowgenius.toolkit.genapidocjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.util.PsiUtil;
import com.slowgenius.toolkit.utils.SlowPsiUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/23 19:40:00
 */

public class GenApiDocJsonAction extends AnAction {

    private static GenApiDocJsonService genApiDocJsonService;

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        genApiDocJsonService = new GenApiDocJsonService(event.getProject());
        GenApiDocContext context = new GenApiDocContext(event.getProject());

        //获取psiClass
        PsiClass psiClass = SlowPsiUtils.getClassByEvent(event);
        context.setJavaClass(psiClass);
        //获取所有接口方法
        List<PsiMethod> allApi = genApiDocJsonService.getAllApi(psiClass);
        context.setApiList(allApi);

        //获取所有body
        List<JSONObject> bodyList = allApi.stream()
                .map(item -> assembleBodyJson(item, context)).collect(Collectors.toList());

        JSONObject schemas = new JSONObject();
        context.getBodyList().forEach(item -> {
            //已经搞过的对象直接跳过
            if (schemas.get(item.getName()) != null) {
                return;
            }
            JSONObject schema = new JSONObject();
            genApiDocJsonService.buildObjectSchema(item);
            schema.put("type", genApiDocJsonService.getBodyType(item));
            schema.put("properties", genApiDocJsonService.buildBodyPropertiesJson(item));
            schema.put("required", genApiDocJsonService.buildBodyRequiredJson(item));
            schema.put("description", SlowPsiUtils.parseComment(item.getDocComment()));
            schema.put("x-apifox-folder", psiClass.getName());
            System.out.println(schema.toJSONString());
            schemas.put(item.getName(), schema);
        });
        JSONObject finalJson = new JSONObject();
        finalJson.put("schemas", schemas);

        JSONObject components = new JSONObject();
        components.put("schemas", schemas);

        finalJson.put("openapi", "3.0.1");
        finalJson.put("components", components);


        List<JSONObject> apiList = Objects.requireNonNull(allApi).stream()
                .map(item -> buildMethodJson(item, context))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        JSONArray jsonArray = new JSONArray(apiList);
        finalJson.put("paths", jsonArray);

        System.out.println(JSONObject.toJSONString(finalJson, SerializerFeature.PrettyFormat));
    }


    private JSONObject buildMethodJson(PsiMethod psiMethod, GenApiDocContext context) {
        //只处理方法注解上有@{}Mapping注解的方法
        Optional<PsiAnnotation> mapping = Arrays.stream(psiMethod.getAnnotations())
                .filter(item -> Optional.ofNullable(item.getQualifiedName()).orElse("").contains("Mapping"))
                .findAny();
        if (mapping.isEmpty()) {
            return null;
        }
        JSONObject result = new JSONObject();
        List<PsiParameter> paramList = Arrays.stream(psiMethod.getParameterList().getParameters()).collect(Collectors.toList());
        //设置parameters
        JSONArray paramArray = assembleParamJson(paramList);
        result.put("parameters", paramArray);
        //设置requestBody
        JSONObject bodyObject = assembleBodyJson(psiMethod, context);
        result.put("requestBody", bodyObject);
        return result;
    }


    private JSONArray assembleParamJson(List<PsiParameter> psiParameter) {
        JSONArray result = new JSONArray();
        psiParameter.forEach(item->{
                    result.add(item.getName());
                });
        return result;
    }

    /**
     * 通过参数列表获取到@ReuqestBody标注的对象，并转换成json
     */
    private JSONObject assembleBodyJson(PsiMethod psiMethod, GenApiDocContext context) {
        List<PsiParameter> psiParameter = Arrays.stream(psiMethod.getParameterList().getParameters()).collect(Collectors.toList());
        if (psiParameter.isEmpty()) {
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
        PsiClass psiClass = PsiUtil.resolveClassInType(requestBodyParam.getType());
        String bodyType = genApiDocJsonService.getBodyType(psiClass);
        context.getBodyList().add(psiClass);
        if (bodyType.equals("object")) {
            return genApiDocJsonService.buildObjectSchema(psiClass);
        }
        JSONObject result = new JSONObject();
        result.put("type", "array");
        String className = requestBodyParam.getType().getCanonicalText().replace("java.util.List<", "").replace(">", "");
        PsiClass itemClass = SlowPsiUtils.getClassByName(genApiDocJsonService.getProject(), className);
        result.put("items", genApiDocJsonService.buildObjectSchema(itemClass));
        return result;
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

}
