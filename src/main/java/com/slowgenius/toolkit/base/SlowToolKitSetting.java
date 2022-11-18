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

    private final HashMap<String, JTextField> configInfo = new HashMap<>();

    public SlowToolKitSetting() {
        this.component = new JPanel();
        this.component.setLayout(new GridLayout(15, 1));
        VisibleConfig state = VisibleConfig.INSTANCE.getState();
        assert state != null;
        state.getProperties().forEach((k, v) -> {
            JTextField temp = new JTextField();
            component.add(temp);
            configInfo.put(k, temp);
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
        Map<String, Boolean> properties = Objects.requireNonNull(VisibleConfig.INSTANCE.getState()).getProperties();
        properties.forEach((k, v) -> {
            if (configInfo.get(k) == null) {
                return;
            }
            properties.put(k, Boolean.valueOf(configInfo.get(k).getText()));
        });
    }
}
