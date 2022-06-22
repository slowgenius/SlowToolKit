package com.mp.tools.generator.ui;

import javax.swing.*;

public class ProjectConfigUI {

    private JPanel mainPanel;
    private JTextField groupIdField;
    private JTextField artifactIdField;

    public JTextField getProjectName() {
        return projectName;
    }

    private JTextField projectName;
    private JTextField packageField;

    public JComponent getComponent(){
        return mainPanel;
    }

    public JTextField getGroupIdField() {
        return groupIdField;
    }

    public JTextField getArtifactIdField() {
        return artifactIdField;
    }

    public JTextField getPackageField() {
        return packageField;
    }

}
