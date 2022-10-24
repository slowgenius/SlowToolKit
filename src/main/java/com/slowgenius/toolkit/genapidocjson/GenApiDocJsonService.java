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
    public List<PsiMethod> getAllApi(PsiClass psiClass) {
        return Arrays.stream(psiClass.getMethods())
                .filter(item -> Arrays.stream(item.getAnnotations())
                        .anyMatch(annotation -> annotation.getQualifiedName().contains("Mapping")))
                .collect(Collectors.toList());
    }


    public JSONObject buildBodyPropertiesJson(PsiClass psiClass) {
        if (psiClass == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        Arrays.stream(psiClass.getAllFields()).forEach(item -> {
            JSONObject param = new JSONObject();
            String type = this.parseBodyFieldType(item.getType());
            if (type.equals("object")) {
                param = buildObjectSchema(SlowPsiUtils.getClassByName(project, item.getType().getCanonicalText()));
            } else if (type.equals("array")) {
                param.put("type", "array");
                JSONObject arrayItem = buildObjectSchema(SlowPsiUtils.getClassByName(project, item.getType().getCanonicalText()));
                param.put("items", arrayItem);
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

    public JSONObject buildObjectSchema(PsiClass psiClass) {
        JSONObject schema = new JSONObject();
        schema.put("type", "object");
        schema.put("properties", buildBodyPropertiesJson(psiClass));
        schema.put("required", buildBodyRequiredJson(psiClass));
        schema.put("description", SlowPsiUtils.parseComment(psiClass.getDocComment()));
        return schema;
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

    public String parseBodyFieldType(PsiType type) {
        if (type.getPresentableText().equals("Integer") || type.getPresentableText().equals("int")) {
            return "integer";
        }
        if (type.getPresentableText().equals("Byte") || type.getPresentableText().equals("byte")) {
            return "integer";
        }
        if (type.getPresentableText().equals("Short") || type.getPresentableText().equals("short")) {
            return "integer";
        }
        if (type.getPresentableText().equals("Long") || type.getPresentableText().equals("long")) {
            return "integer";
        }

        if (type.getPresentableText().equals("Double") || type.getPresentableText().equals("double")) {
            return "number";
        }
        if (type.getPresentableText().equals("Float") || type.getPresentableText().equals("float")) {
            return "number";
        }
        if (type.getPresentableText().equals("Boolean") || type.getPresentableText().equals("boolean")) {
            return "boolean";
        }
        if (type.getPresentableText().equals("Date")) {
            return "string";
        }
        if (type.getPresentableText().equals("LocalDateTime")) {
            return "string";
        }
        if (type.getPresentableText().equals("LocalDate")) {
            return "string";
        }
        if (type.getPresentableText().equals("LocalTime")) {
            return "string";
        }
        if (type.getPresentableText().equals("String")) {
            return "string";
        }
        if (type.getPresentableText().contains("List") ||
                type.getPresentableText().contains("Collection") ||
                type.getPresentableText().contains("Set")) {
            return "array";
        }

        return "object";
    }
}
