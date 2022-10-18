package com.slowgenius.toolkit.converttojson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.PsiTypeParameterListOwner;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.util.PsiUtil;
import com.slowgenius.toolkit.utils.PsiUtils;
import com.slowgenius.toolkit.utils.StrUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/17 22:20:42
 */

public class ConvertToJsonAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        PsiElement data = Objects.requireNonNull(event.getData(CommonDataKeys.PSI_FILE)).findElementAt(Objects.requireNonNull(event.getData(CommonDataKeys.EDITOR)).getCaretModel().getOffset());
        assert data != null;
        PsiClass psiClass = (PsiClass) data.getParent();
        assert psiClass != null;
        JSONObject jsonObject = new JSONObject();
        Arrays.stream(psiClass.getAllFields()).forEach(item -> {
            fillInParam(item, jsonObject);
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

    private String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    private JSONObject fillInParam(PsiField field, JSONObject jsonObject) {
        if (field.getType().getPresentableText().contains("String")) {
            jsonObject.put(field.getName(), randomString(8));
            return jsonObject;
        }
        if (field.getType().getPresentableText().contains("Integer") || field.getType().getPresentableText().contains("Double")) {
            jsonObject.put(field.getName(), randomString(8));
            return jsonObject;
        }
        PsiClass obj = PsiUtil.resolveClassInType(field.getType());
        if (obj == null) {
            return jsonObject;
        }

        if (Objects.requireNonNull(obj.getName()).contains("List")) {
            String className = field.getType().getCanonicalText().replace("java.util.List<", "").replace(">", "");
            obj = PsiUtils.getBu(ProjectManager.getInstance().getDefaultProject(), className);
        }
        if (obj == null) {
            return jsonObject;
        }

        Map<String, PsiField> collect = Arrays.stream(obj.getFields()).collect(Collectors.toMap(PsiField::getName, Function.identity()));
        List<PsiField> fieldList = Arrays.stream(Objects.requireNonNull(obj).getMethods())
                .filter(Objects::nonNull)
                .filter(item -> item.getName().startsWith("get"))
                .map(item -> collect.getOrDefault(StrUtils.firstLetterLower(item.getName().replace("get", "")), null)).filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (fieldList.size() == 0) {
            jsonObject.put(field.getName(), "");
            return jsonObject;
        }
        JSONObject fieldObject = new JSONObject();
        fieldList.forEach(item -> {
            fillInParam(item, fieldObject);
        });
        jsonObject.put(field.getName(), fieldObject);
        return jsonObject;
    }
}
