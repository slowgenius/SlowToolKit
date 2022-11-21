package com.slowgenius.toolkit.toolkit.ui;


import com.intellij.openapi.project.Project;
import com.slowgenius.toolkit.toolkit.data.LanguageData;
import com.slowgenius.toolkit.toolkit.data.Translator;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class TranslatorUI {

    private JPanel main;
    private JTextPane result;
    private JButton submit;
    private JTextArea text;

    private JLabel tips;
    private JComboBox<String> comboBox;

    private final Project project;

    public TranslatorUI(Project project) {
        this.project = project;
        ActionListener actionListener = e -> translateAction();
        text.addKeyListener(new KeyAdapter() {
            //control + enter按下
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isAltDown() && e.getKeyChar() == '\r') {
                    translateAction();
                }
            }
        });
        submit.addActionListener(actionListener);
    }

    private void translateAction() {
        String str = getText().getText();
        String language = LanguageData.getLanguage(str);
        if (language.equals("en") && Objects.requireNonNull(comboBox.getSelectedItem()).toString().equals("English")) {
            comboBox.setSelectedIndex(1);
        }
        if (language.equals("zh") && Objects.requireNonNull(comboBox.getSelectedItem()).toString().equals("中文")) {
            comboBox.setSelectedIndex(0);
        }
        String to;
        switch (comboBox.getSelectedIndex()) {
            case 0:
                to = "en";
                break;
            case 1:
                to = "zh";
                break;
            case 2:
                to = "yue";
                break;
            case 3:
                to = "wyw";
                break;
            case 4:
                to = "cht";
                break;
            case 5:
                to = "jp";
                break;
            default:
                to = "";
        }
        result.setText(Translator.translate(language, to, str));
    }

    public JPanel getMain() {
        return main;
    }


    public JTextArea getText() {
        return text;
    }

    public JLabel getTips() {
        return tips;
    }


    public Project getProject() {
        return project;
    }
}
