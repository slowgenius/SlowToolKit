package com.slowgenius.toolkit.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 插件是否可见配置
 *
 * @author hty
 * date: 2022/11/9
 */
public class VisibleConfig {

    public static VisibleConfig INSTANCE = new VisibleConfig();
    private Map<String, Boolean> properties = new HashMap<>();

    static {
        VisibleConfig.INSTANCE.properties.put("create classes", true);
        VisibleConfig.INSTANCE.properties.put("one", true);
        VisibleConfig.INSTANCE.properties.put("two", true);
        VisibleConfig.INSTANCE.properties.put("three", true);
    }

    public Map<String, Boolean> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Boolean> properties) {
        this.properties = properties;
    }
}
