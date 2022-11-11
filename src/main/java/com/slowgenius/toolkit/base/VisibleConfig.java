package com.slowgenius.toolkit.base;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
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

    public static VisibleConfig INSTANCE = new VisibleConfig();
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
        if (visibleConfig.properties == null) {
            visibleConfig.properties = new HashMap<>();
            properties.put("", true);
        }
        this.properties = visibleConfig.properties;
    }

    public static VisibleConfig getInstance(Project project) {
        return project.getService(VisibleConfig.class);
    }

    public static VisibleConfig getInstance() {
        return ProjectManager.getInstance().getDefaultProject().getService(VisibleConfig.class);
    }
}
