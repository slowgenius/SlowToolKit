package com.mp.tools.findName.ui;


import com.mp.tools.findName.data.NameData;

import javax.swing.*;

public class ConsoleUI {

    private JPanel main;
    private JTextPane text;
    private JTextPane result;
    private JButton submit;

    public ConsoleUI() {
        submit.addActionListener(e -> {
            NameData data = NameData.getData(getText().getText());
            String str = "";
            for (String allWord : data.getAllWords()) {
                str = str + " [" + allWord + "] ";
            }
            result.setText(str);
        });
    }

    public JTextPane getText() {
        return text;
    }

    public void setText(JTextPane text) {
        this.text = text;
    }

    public JPanel getMain() {
        return main;
    }

    public void setMain(JPanel main) {
        this.main = main;
    }
}
