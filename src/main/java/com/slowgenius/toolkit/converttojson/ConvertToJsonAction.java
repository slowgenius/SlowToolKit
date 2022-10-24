package com.slowgenius.toolkit.converttojson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiUtil;
import com.slowgenius.toolkit.utils.SlowPsiUtils;
import com.slowgenius.toolkit.utils.SlowRandomUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/17 22:20:42
 */

public class ConvertToJsonAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PsiClass psiClass = SlowPsiUtils.getClassByEvent(event);
        assert psiClass != null;
        JSONObject jsonObject = new JSONObject();
        List<PsiField> fieldList = Arrays.stream(psiClass.getAllFields()).collect(Collectors.toList());
        fieldList.forEach(item -> {
            fillInParam(item, jsonObject, event.getProject());
        });
        String s = JSONObject.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
        StringSelection selection = new StringSelection(s);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);


    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        PsiElement data = Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).findElementAt(Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getCaretModel().getOffset());
        if (data == null) {
            event.getPresentation().setEnabledAndVisible(false);
            return;
        }
        if (data.getParent() instanceof PsiClass) {
            event.getPresentation().setEnabledAndVisible(true);
            return;
        }
        event.getPresentation().setEnabledAndVisible(false);
    }

    private void fillInParam(PsiField field, JSONObject jsonObject, Project project) {
        //如果处理了基本类型，直接返回
        if (handleNormalType(field, jsonObject)) {
            return;
        }
        String fieldName = Objects.requireNonNull(field.getName());
        if (fieldName.contains("List") || fieldName.contains("Set") || fieldName.contains("Collection")) {
            handleArray(field, jsonObject, project);
            return;
        }
        handleObject(field, jsonObject, project);
    }


    public boolean handleNormalType(PsiField field, JSONObject jsonObject) {
        if (field.getType().getPresentableText().equals("Integer") || field.getType().getPresentableText().equals("int")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomInteger(1, 10));
            return true;
        }
        if (field.getType().getPresentableText().equals("Byte") || field.getType().getPresentableText().equals("byte")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomInteger(1, 10));
            return true;
        }
        if (field.getType().getPresentableText().equals("Short") || field.getType().getPresentableText().equals("short")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomInteger(1, 10));
            return true;
        }
        if (field.getType().getPresentableText().equals("Long") || field.getType().getPresentableText().equals("long")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomInteger(1000, 9999));
            return true;
        }
        if (field.getType().getPresentableText().equals("Double") || field.getType().getPresentableText().equals("double")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomInteger(1, 10));
            return true;
        }
        if (field.getType().getPresentableText().equals("Float") || field.getType().getPresentableText().equals("float")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomInteger(1, 10));
            return true;
        }
        if (field.getType().getPresentableText().equals("Boolean") || field.getType().getPresentableText().equals("boolean")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomInteger(1, 10));
            return true;
        }
        if (field.getType().getPresentableText().equals("Character") || field.getType().getPresentableText().equals("char")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomChar());
            return true;
        }

        if (field.getType().getPresentableText().equals("String")) {
            jsonObject.put(field.getName(), SlowRandomUtils.randomString(8));
            return true;
        }
        if (field.getType().getPresentableText().equals("Date")) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            jsonObject.put(field.getName(), time);
            return true;
        }
        if (field.getType().getPresentableText().equals("LocalDateTime")) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            jsonObject.put(field.getName(), time);
            return true;
        }
        if (field.getType().getPresentableText().equals("LocalDate")) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            jsonObject.put(field.getName(), time);
            return true;
        }
        if (field.getType().getPresentableText().equals("LocalTime")) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            jsonObject.put(field.getName(), time);
            return true;
        }
        if (field.getType().getPresentableText().contains("Map")) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("hashKey", "hashValue");
            jsonObject.put(field.getName(), tempObject);
            return true;
        }
        return false;
    }

    public void handleObject(PsiField field, JSONObject jsonObject, Project project) {
        PsiClass obj = PsiUtil.resolveClassInType(field.getType());
        if (obj == null || obj.getFields().length == 0) {
            return;
        }
        List<PsiField> fieldList = SlowPsiUtils.getFields(obj);
        //没有属性就填充空字段
        if (fieldList.size() == 0) {
            jsonObject.put(field.getName(), new JSONObject());
            return;
        }
        JSONObject fieldObject = new JSONObject();
        fieldList.forEach(item -> {
            fillInParam(item, fieldObject, project);
        });
        jsonObject.put(field.getName(), fieldObject);
    }

    public void handleArray(PsiField field, JSONObject jsonObject, Project project) {
        String className = field.getType().getCanonicalText().replace("java.util.List<", "").replace(">", "");
        PsiClass psiClass = SlowPsiUtils.getClassByName(project, className);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < SlowRandomUtils.randomInteger(1, 3); i++) {
            JSONObject tempObject = new JSONObject();
            SlowPsiUtils.getFields(psiClass).forEach(item -> {
                fillInParam(item, tempObject, project);
            });
            jsonArray.add(tempObject);
        }
        jsonObject.put(field.getName(), jsonArray);
    }

}
