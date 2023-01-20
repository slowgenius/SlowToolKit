package com.slowgenius.toolkit.toolkit.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2023/1/18 19:32:24
 */

public class RegularExpression {

    private final Project project;

    private JPanel main;
    private JTextArea text;
    private JTextField expression;
    private JButton submit;

    public RegularExpression(Project project) {

        this.project = project;
        submit.addActionListener(action -> {
            Pattern pattern = Pattern.compile(expression.getText());
            Matcher matcher = pattern.matcher(text.getText());
            Highlighter highlighter = text.getHighlighter();
            highlighter.removeAllHighlights();
            DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(JBColor.ORANGE);
            while (matcher.find()) {
                try {
                    highlighter.addHighlight(matcher.start(), matcher.end(), highlightPainter);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public JPanel getMain() {
        return main;
    }


    public static void main(String[] args) {
        System.out.println(1);
    }
}
