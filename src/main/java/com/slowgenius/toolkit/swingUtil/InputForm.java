package com.slowgenius.toolkit.swingUtil;

import javax.swing.*;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2023/1/18 18:31:23
 */

public class InputForm {

    private JLabel label;
    private JPanel main;
    private JTextArea text;

    public InputForm(String label) {
        this.label.setText(label);
    }

    public JTextArea getText() {
        return text;
    }


    public void setText(String str) {
        text.setText(str);
    }

    public InputForm(String label, boolean enable) {
        this.label.setText(label);
        this.text.setEnabled(enable);
    }

    public JPanel getMain() {
        return main;
    }


    public JTextArea getTextField() {
        return text;
    }


    public JLabel getLabel() {
        return label;
    }

}
