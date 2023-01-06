package com.slowgenius.toolkit.toolkit.ui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.slowgenius.toolkit.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
    private JTextArea text;
    private JTextField jsonPath;
    private JButton submit;
    private JPanel inputs;
    private JPanel results;
    private final Project project;


    public JsonFormatter(Project project) {
        this.project = project;
        text.addKeyListener(new KeyAdapter() {
            //option + enter按下
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    formatAction();
                }
            }
        });
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        submit.addActionListener(action -> formatAction());
        main.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             * @param e
             */
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension mainSize = main.getSize();
                inputs.setMinimumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                inputs.setMaximumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                results.setMinimumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                results.setMaximumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
            }
        });
    }

    public void formatAction() {
        String unFormatJson = text.getText();
        if (!JSONObject.isValid(unFormatJson)) {
            Notifications.Bus.notify(new Notification("Print", "Json格式化失败", "invalid json" + System.currentTimeMillis(), NotificationType.INFORMATION), project);
        }
        Object unFormatJsonObject;
        if (StringUtils.isNotBlank(jsonPath.getText())) {
            unFormatJsonObject = JSONUtils.read(unFormatJson, jsonPath.getText());
        } else {
            unFormatJson = unFormatJson.trim();
            if (unFormatJson.startsWith("{")) {
                unFormatJsonObject = JSONObject.parseObject(unFormatJson);
            } else if (unFormatJson.startsWith("[")) {
                unFormatJsonObject = JSONObject.parseArray(unFormatJson);
            }else {
                unFormatJsonObject = "json格式有误";
            }
        }
        String resultJson = JSON.toJSONString(unFormatJsonObject, SerializerFeature.PrettyFormat).replace("\t", "    ");
        result.setText(resultJson);
    }

    public JPanel getMain() {
        return main;
    }


    public Project getProject() {
        return project;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        inputs = new JPanel();
        results = new JPanel();
    }
}
