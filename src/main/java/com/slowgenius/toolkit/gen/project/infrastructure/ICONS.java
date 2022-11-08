package com.slowgenius.toolkit.gen.project.infrastructure;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class ICONS {

    private static Icon load(String path) {
        return IconLoader.getIcon(path, ICONS.class);
    }

    public static final Icon DDD = load("/icons/DDD.png");

    public static final Icon SERVER_ICON = load("/icons/server.png");

    public static final Icon API_ICON = load("/icons/api.png");

}
