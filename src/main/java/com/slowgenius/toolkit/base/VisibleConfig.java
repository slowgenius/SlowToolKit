package com.slowgenius.toolkit.base;

import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.slowgenius.toolkit.autoCreate.CreateClasses;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 插件是否可见配置
 *
 * @author hty
 * date: 2022/11/9
 */
@State(name = "visibleConfig", storages = {@Storage(value = "visibleConfig.xml")})
public class VisibleConfig implements PersistentStateComponent<VisibleConfig> {
    private Map<String, Boolean> properties = new HashMap<>();


    public Map<String, Boolean> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Boolean> properties) {
        this.properties = properties;
    }

    @Override
    public @Nullable VisibleConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull VisibleConfig visibleConfig) {
        this.properties = visibleConfig.properties;
    }


    public static VisibleConfig getInstance() {
        VisibleConfig visibleConfig = ApplicationManager.getApplication().getService(VisibleConfig.class);
        if (visibleConfig.getProperties().isEmpty()) {
            visibleConfig.init();
        }
        return visibleConfig;
    }

    private void init() {
        this.properties = new HashMap<>();
        CreateClasses action = (CreateClasses) ActionUtil.getAction("CreateClasses");
        assert action != null;
        properties.put(action.getActionKey(), true);
    }
}
