package com.slowgenius.toolkit.base;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 插件配置项
 *
 * @author hty
 * date: 2022/11/9
 */
public class SlowToolKitSetting implements Configurable {
    JComponent component;

    JTextField something;

    public SlowToolKitSetting() {
        this.component = new JPanel();
        this.component.setLayout(new GridLayout(15, 1));

        // 创建appID、securityKey文本框
        this.something = new JTextField();
        something.setText("soemthing test");
        component.add(something);
    }


    @Override
    public String getDisplayName() {
        return "SlowToolKit";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return component;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    // 点击配置页面中的 apply 按钮或者 OK 按钮，会调用该方法，在该方法中保存配置
    @Override
    public void apply() throws ConfigurationException {
        VisibleConfig.INSTANCE.getProperties().put("create classes", Boolean.valueOf(something.getText()));
    }
}
