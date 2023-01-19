package com.slowgenius.toolkit.toolkit.ui;

import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2023/1/18 19:32:24
 */

public class RegularExpression {

    private final Project project;

    private JPanel main;

    public RegularExpression(Project project) {
        this.project = project;
    }


    public JPanel getMain() {
        return main;
    }
}
