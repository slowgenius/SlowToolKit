package com.slowgenius.toolkit.genapidocjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.slowgenius.toolkit.utils.SlowPsiUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/24 21:11:37
 */
public class GenApiDocJsonService {

    public GenApiDocJsonService(Project project) {
        this.project = project;
    }

    private final Project project;


    public Project getProject() {
        return project;
    }

    /**
     * 通过psiClass获取所有方法
     */
    public void getAllApi(PsiClass psiClass, GenApiDocContext context) {
        List<PsiMethod> mapping = Arrays.stream(psiClass.getMethods())
                .filter(item -> Arrays.stream(item.getAnnotations())
                        .anyMatch(annotation -> annotation.getQualifiedName().contains("Mapping")))
                .collect(Collectors.toList());
        context.setApiList(mapping);
    }


    public JSONObject buildBodyPropertiesJson(PsiType psiType) {
        PsiClass psiClass = SlowPsiUtils.getClassByTYpe(psiType);
        return buildBodyPropertiesJson(psiClass);
    }

    public JSONObject buildBodyPropertiesJson(PsiClass psiClass) {
        if (psiClass == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        Arrays.stream(psiClass.getAllFields()).forEach(item -> {
            JSONObject param = new JSONObject();
            String type = this.parseBodyFieldType(item.getType().getPresentableText());
            if (type.equals("object") || type.equals("array")) {
                param = buildObjectSchema(item.getType());
            } else {
                param.put("type", type);
                param.put("description", SlowPsiUtils.parseComment(item.getDocComment()));
            }
            jsonObject.put(item.getName(), param);
        });
        return jsonObject;
    }

    public String getBodyType(PsiClass psiClass) {
        if (psiClass.getQualifiedName().contains("List") ||
                psiClass.getQualifiedName().contains("Collection") ||
                psiClass.getQualifiedName().contains("Set")) {
            return "array";
        }
        return "object";
    }

    public JSONObject buildObjectSchema(PsiType psiType) {
        PsiClass psiClass = SlowPsiUtils.getClassByTYpe(psiType);
        JSONObject schema = new JSONObject();
        String bodyType = getBodyType(psiClass);
        if (bodyType.equals("array")) {
            buildArray(schema, psiType);
            return schema;
        }
        buildObject(schema, psiClass);
        return schema;
    }

    public void buildArray(JSONObject schema, PsiType psiType) {
        schema.put("type", "array");
        String className = psiType.getCanonicalText().replace("java.util.List<", "").replace(">", "");
        String itemType = parseBodyFieldType(className);
        if (itemType.equals("object")) {
            PsiClass classByName = SlowPsiUtils.getClassByName(project, className);
            JSONObject object = new JSONObject();
            buildObject(object, classByName);
            schema.put("items", object);
            return;
        }
        JSONObject item = new JSONObject();
        item.put("type", itemType);
        schema.put("item", item);
    }

    public void buildObject(JSONObject jsonObject, PsiClass psiClass) {
        jsonObject.put("type", "object");
        jsonObject.put("properties", buildBodyPropertiesJson(psiClass));
        jsonObject.put("required", buildBodyRequiredJson(psiClass));
        jsonObject.put("description", SlowPsiUtils.parseComment(psiClass.getDocComment()));
    }

    public JSONArray buildBodyRequiredJson(PsiType psiType) {
        PsiClass psiClass = SlowPsiUtils.getClassByTYpe(psiType);
        return buildBodyRequiredJson(psiClass);
    }

    public JSONArray buildBodyRequiredJson(PsiClass psiClass) {
        JSONArray jsonArray = new JSONArray();
        SlowPsiUtils.getFields(psiClass).stream()
                .filter(item -> Arrays.stream(item.getAnnotations()).anyMatch(annotation -> annotation.getQualifiedName().contains("NotBlank") || annotation.getQualifiedName().contains("NotNull")))
                .forEach(item -> {
                    jsonArray.add(item.getName());
                });
        return jsonArray;
    }

    public String parseBodyFieldType(String type) {
        if (type.equals("Integer") || type.equals("int")) {
            return "integer";
        }
        if (type.equals("Byte") || type.equals("byte")) {
            return "integer";
        }
        if (type.equals("Short") || type.equals("short")) {
            return "integer";
        }
        if (type.equals("Long") || type.equals("long")) {
            return "integer";
        }

        if (type.equals("Double") || type.equals("double")) {
            return "number";
        }
        if (type.equals("Float") || type.equals("float")) {
            return "number";
        }
        if (type.equals("Boolean") || type.equals("boolean")) {
            return "boolean";
        }
        if (type.equals("Date")) {
            return "string";
        }
        if (type.equals("LocalDateTime")) {
            return "string";
        }
        if (type.equals("LocalDate")) {
            return "string";
        }
        if (type.equals("LocalTime")) {
            return "string";
        }
        if (type.equals("String")) {
            return "string";
        }
        if (type.contains("List") ||
                type.contains("Collection") ||
                type.contains("Set")) {
            return "array";
        }

        return "object";
    }
}
