package com.slowgenius.toolkit.toolkit.ui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/16 15:05:13
 */

public class JsonFormatter {

    private JPanel main;
    private JTextArea result;
    private JButton submit;
    private JTextArea text;

    private JLabel tips;
    private JComboBox<String> checkBox;

    private final Project project;


    public JsonFormatter(Project project) {
        this.project = project;
        text.addKeyListener(new KeyAdapter() {
            //control + enter按下
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isAltDown() && e.getKeyChar() == '\r') {
                    formatAction();
                }
            }
        });
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        submit.addActionListener(e -> formatAction());
    }

    public void formatAction() {
        String unFormatJson = text.getText();
        if (!JSONObject.isValid(unFormatJson)) {
            Notifications.Bus.notify(new Notification("Print", "Json格式化失败", "invalid json" + System.currentTimeMillis(), NotificationType.INFORMATION), project);
        }
        JSONObject unFormatJsonObject = JSONObject.parseObject(unFormatJson);
        String resultJson = JSON.toJSONString(unFormatJsonObject, SerializerFeature.PrettyFormat);
        result.setText(resultJson);
    }

    public JPanel getMain() {
        return main;
    }

    public void setMain(JPanel main) {
        this.main = main;
    }

    public JTextArea getResult() {
        return result;
    }

    public void setResult(JTextArea result) {
        this.result = result;
    }

    public JButton getSubmit() {
        return submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JTextArea getText() {
        return text;
    }

    public void setText(JTextArea text) {
        this.text = text;
    }

    public JLabel getTips() {
        return tips;
    }

    public void setTips(JLabel tips) {
        this.tips = tips;
    }

    public JComboBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JComboBox checkBox) {
        this.checkBox = checkBox;
    }

    public Project getProject() {
        return project;
    }
}
