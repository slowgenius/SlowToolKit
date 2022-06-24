package com.mp.tools.generator.ui;

import javax.swing.*;

public class ProjectServerConfigUI {

    private JPanel mainPanel;

    public JTextField getProjectName() {
        return projectName;
    }

    private JTextField projectName;

    public JComponent getComponent() {
        return mainPanel;
    }

}
