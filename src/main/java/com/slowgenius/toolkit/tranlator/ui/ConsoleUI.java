package com.slowgenius.toolkit.tranlator.ui;


import com.slowgenius.toolkit.tranlator.data.LanguageData;
import com.slowgenius.toolkit.tranlator.data.Translator;

import javax.swing.*;
import java.util.Objects;

public class ConsoleUI {

    private JPanel main;
    private JTextPane result;
    private JButton submit;
    private JTextArea text;

    private JLabel tips;
    private JComboBox comboBox1;

    public ConsoleUI() {
        submit.addActionListener(e -> {
            String str = getText().getText();
            String language = LanguageData.getLanguage(str);
            if (language.equals("en") && Objects.requireNonNull(comboBox1.getSelectedItem()).toString().equals("English")) {
                comboBox1.setSelectedIndex(1);
            }
            if (language.equals("zh") && Objects.requireNonNull(comboBox1.getSelectedItem()).toString().equals("中文")) {
                comboBox1.setSelectedIndex(0);
            }
            String to;
            switch (comboBox1.getSelectedIndex()) {
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
        });
    }

    public JPanel getMain() {
        return main;
    }

    public void setMain(JPanel main) {
        this.main = main;
    }

    public JTextArea getText() {
        return text;
    }

    public void setText(JTextArea text) {
        this.text = text;
    }

    public JLabel getTips() {
        return tips;
    }

    public void setTips(JLabel tips) {
        this.tips = tips;
    }
}
