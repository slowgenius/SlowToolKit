package com.slowgenius.toolkit.gen.apidocjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
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

        //获取controller
        PsiClass psiClass = SlowPsiUtils.getClassByEvent(event);
        context.setController(psiClass);
        //获取所有接口方法
        genApiDocJsonService.getAllApi(psiClass, context);
        //获取所有body
        context.getApiList().forEach(item -> parseRequestBody(item, context));

        JSONObject schemas = new JSONObject();
        context.getBodyList().forEach(item -> {
            PsiClass itemClass = SlowPsiUtils.getClassByTYpe(item);
            //已经搞过的对象直接跳过
            if (schemas.get(itemClass.getName()) != null) {
                return;
            }
            JSONObject schema = genApiDocJsonService.buildObjectSchema(item);
            schema.put("x-apifox-folder", psiClass.getName());
            schemas.put(itemClass.getName(), schema);
        });

        JSONObject finalJson = new JSONObject();

        JSONObject components = new JSONObject();
        components.put("schemas", schemas);

        finalJson.put("openapi", "3.0.1");
        finalJson.put("components", components);

        JSONObject pathJson = new JSONObject();

        Objects.requireNonNull(context.getApiList())
                .forEach(item -> {
                    Optional<PsiAnnotation> mapping = Arrays.stream(item.getAnnotations()).filter(annotation -> annotation.getText().contains("Mapping")).findAny();
                    JSONObject jsonObject = buildMethodJson(item, context);
                    if (jsonObject == null) {
                        return;
                    }
                    String path = mapping.get().getText().replaceAll("@\\w+Mapping", "")
                            .replace("\"", "")
                            .replace("(", "")
                            .replace(")", "");
                    path = "/" + context.getBasePath() + path;

                    JSONObject requestTypeJson = new JSONObject();
                    String requestType = mapping.get().getText().replaceAll("@(\\w*)Mapping.*", "$1");
                    if (requestType.equals("Request")) {
                        requestType = "post";
                    }
                    jsonObject.put("x-apifox-folder", context.getController().getName());
                    jsonObject.put("description", SlowPsiUtils.parseComment(context.getController().getDocComment()));
                    requestTypeJson.put(requestType.toLowerCase(), jsonObject);

                    pathJson.put(path.replace("//", "/"), requestTypeJson);
                });

        finalJson.put("paths", pathJson);

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
        JSONObject requestBody = buildBodyJson(psiMethod, context);
        if (requestBody != null) {
            result.put("requestBody", requestBody);
        }
        return result;
    }


    private JSONArray assembleParamJson(List<PsiParameter> psiParameter) {
        JSONArray result = new JSONArray();
        psiParameter.forEach(item -> {
            result.add(item.getName());
        });
        return result;
    }

    /**
     * 通过参数列表获取到@ReuqestBody标注的对象，并转换成json
     */
    private void parseRequestBody(PsiMethod psiMethod, GenApiDocContext context) {
        List<PsiParameter> psiParameter = Arrays.stream(psiMethod.getParameterList().getParameters()).collect(Collectors.toList());
        if (psiParameter.isEmpty()) {
            return;
        }
        //获取requestBody注解标注的参数
        Optional<PsiParameter> requestBodyOpt = psiParameter.stream()
                .filter(item -> Arrays.stream(item.getAnnotations())
                        .anyMatch(psiAnnotation -> Optional.ofNullable(psiAnnotation.getQualifiedName()).orElse("").contains("RequestBody")))
                .findAny();
        if (requestBodyOpt.isEmpty()) {
            return;
        }
        PsiParameter requestBodyParam = requestBodyOpt.get();
        context.getBodyList().add(requestBodyParam.getType());
        context.getMethodBodyMap().put(psiMethod, requestBodyParam.getType());
    }


    /**
     * 传入schema信息，直接构建出requestBody节点
     */
    private JSONObject buildBodyJson(PsiMethod schema, GenApiDocContext context) {
        PsiType psiType = context.getMethodBodyMap().get(schema);
        if (psiType == null) {
            return null;
        }
        JSONObject result = new JSONObject();
        JSONObject contentObject = new JSONObject();
        JSONObject applicationObject = new JSONObject();
        JSONObject schemaObject = new JSONObject();
        schemaObject.put("$ref", "#/components/schemas/" + psiType.getPresentableText());
        applicationObject.put("schema", schemaObject);
        contentObject.put("application/json", applicationObject);
        result.put("content", contentObject);
        return result;
    }

}
