package com.slowgenius.toolkit.base;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 插件配置项
 *
 * @author hty
 * date: 2022/11/9
 */
public class SlowToolKitSetting implements Configurable {
    JComponent component;

    private final HashMap<String, JRadioButton> configInfo = new HashMap<>();

    public SlowToolKitSetting() {
        this.component = new JPanel();
        this.component.setLayout(new GridLayout(15, 1));
        VisibleConfig state = VisibleConfig.getInstance();
        state.getProperties().forEach((k, v) -> {
            JPanel jPanel = new JPanel();
            JLabel jLabel = new JLabel(k + " : ");
            JRadioButton onButton = new JRadioButton("on");
            JRadioButton offButton = new JRadioButton("off");
            jPanel.add(jLabel);
            jPanel.add(onButton);
            jPanel.add(offButton);
            if (state.getProperties().get(k)) {
                onButton.setSelected(true);
            } else {
                offButton.setSelected(true);
            }
            component.add(jPanel);
            configInfo.put(k, onButton);
        });
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
    public void apply() {
        Map<String, Boolean> properties = Objects.requireNonNull(VisibleConfig.getInstance().getState()).getProperties();
        properties.forEach((k, v) -> {
            if (configInfo.get(k) == null) {
                return;
            }
            properties.put(k, configInfo.get(k).isSelected());
        });
    }
}
