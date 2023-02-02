package com.slowgenius.toolkit.toolkit.ui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.slowgenius.toolkit.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/16 15:05:13
 */

public class JsonFormatter {

    private JPanel main;
    private JTextArea result;
    private JTextArea text;
    private JTextField jsonPath;
    private JButton submit;
    private JPanel inputs;
    private JTree jsonTree;
    private JTabbedPane tabPanel;
    private final Project project;


    public JsonFormatter(Project project) {
        this.project = project;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();

        DefaultTreeModel newModel = new DefaultTreeModel(root);
        jsonTree.setModel(newModel);
        jsonTree.setCellRenderer(new TextPaneDefaultTreeCellRenderer());
        text.addKeyListener(new KeyAdapter() {
            //option + enter按下
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    formatAction();
                }
            }
        });
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        submit.addActionListener(action -> formatAction());
        main.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             * @param e
             */
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension mainSize = main.getSize();
                inputs.setMinimumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                inputs.setMaximumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                tabPanel.setMinimumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                tabPanel.setMaximumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
            }
        });
    }

    public void formatAction() {
        String unFormatJson = text.getText();
        if (!JSONObject.isValid(unFormatJson)) {
            Notifications.Bus.notify(new Notification("Print", "Json格式化失败", "invalid json" + System.currentTimeMillis(), NotificationType.INFORMATION), project);
        }
        Object unFormatJsonObject;
        if (StringUtils.isNotBlank(jsonPath.getText())) {
            unFormatJsonObject = JSONUtils.read(unFormatJson, jsonPath.getText());
        } else {
            unFormatJson = unFormatJson.trim();
            if (unFormatJson.startsWith("{")) {
                unFormatJsonObject = JSONObject.parseObject(unFormatJson);
                JSONObject jsonObject = (JSONObject) unFormatJsonObject;
                DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode("object");
                TreeModel rootModel = new DefaultTreeModel(defaultMutableTreeNode);
                buildTree(jsonObject, defaultMutableTreeNode);
                jsonTree.setModel(rootModel);
                jsonObject.values().forEach(item -> {
                    System.out.println(1111);
                });
            } else if (unFormatJson.startsWith("[")) {
                unFormatJsonObject = JSONObject.parseArray(unFormatJson);
            } else {
                unFormatJsonObject = "json格式有误";
            }
        }
        String resultJson = JSON.toJSONString(unFormatJsonObject, SerializerFeature.PrettyFormat).replace("\t", "    ");
        result.setText(resultJson);
    }


    private DefaultMutableTreeNode buildTree(JSONObject jsonObject, DefaultMutableTreeNode root) {
        jsonObject.keySet().forEach(item -> {
            if (jsonObject.get(item).getClass().getName().contains("JSONObject")) {
                DefaultMutableTreeNode newChild = buildTree((JSONObject) jsonObject.get(item), new DefaultMutableTreeNode("\"" + item + "\":"));
                root.add(newChild);
            } else {
                DecoratedText[] arrayColorText;
                if (jsonObject.get(item).getClass().getName().contains("String")) {
                    arrayColorText = new DecoratedText[]{
                            new DecoratedText(null, "\"" + item + "\"", JBColor.PINK),
                            new DecoratedText(null, ": ", JBColor.GRAY),
                            new DecoratedText(null, "\"" + jsonObject.get(item).toString() + "\"", JBColor.BLUE)
                    };
                } else {
                    arrayColorText = new DecoratedText[]{
                            new DecoratedText(null, "\"" + item + "\"", JBColor.PINK),
                            new DecoratedText(null, ": ", JBColor.GRAY),
                            new DecoratedText(null, jsonObject.get(item).toString(), JBColor.GREEN)};
                }
                root.add(new DefaultMutableTreeNode(arrayColorText));
            }
        });
        return root;
    }


    public JPanel getMain() {
        return main;
    }


    public Project getProject() {
        return project;
    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//        inputs = new JPanel();
//        results = new JPanel();
//    }

}

class DecoratedText {
    private String text;
    private Color background = new JLabel().getBackground();
    private Color foreground = new JLabel().getForeground();
    private Font font = new JLabel().getFont();

    public DecoratedText(String text) {
        this.text = text;
    }

    public DecoratedText(String text, Font font) {
        this.text = text;
        this.font = font;
    }

    public DecoratedText(String text, Color foreground) {
        this.text = text;
        this.foreground = foreground;
    }

    public DecoratedText(String text, Color foreground, Font font) {
        this.text = text;
        this.foreground = foreground;
        this.font = font;
    }

    public DecoratedText(Color background, String text) {
        this.background = background;
        this.text = text;
    }

    public DecoratedText(Color background, String text, Font font) {
        this.background = background;
        this.text = text;
        this.font = font;
    }

    public DecoratedText(Color background, String text, Color foreground) {
        this.background = background;
        this.text = text;
        this.foreground = foreground;
    }

    public DecoratedText(Color background, String text, Color foreground, Font font) {
        this.background = background;
        this.text = text;
        this.foreground = foreground;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public String toString() {
        return "DecoratedText{" + "text=" + text + ", background=" + background
                + ", foreground=" + foreground + ", font=" + font + '}';
    }
}

class TextPaneDefaultTreeCellRenderer extends DefaultTreeCellRenderer {
    TextPaneScrollPane textPaneScrollPane = new TextPaneScrollPane();

    public TextPaneDefaultTreeCellRenderer() {
        textPaneScrollPane.setBackgroundNonSelectionColor(getBackgroundNonSelectionColor());
        textPaneScrollPane.setBackgroundSelectionColor(getBackgroundSelectionColor());
        textPaneScrollPane.setTextNonSelectionColor(getTextNonSelectionColor());
        textPaneScrollPane.setTextSelectionColor(getTextSelectionColor());
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        setIcon(null);
        if (leaf) {
            return textPaneScrollPane.getTreeCellRendererComponent(tree, value,
                    selected, expanded, leaf, row, hasFocus);
        } else {
            return super.getTreeCellRendererComponent(tree, value, selected,
                    expanded, leaf, row, hasFocus);
        }
    }
}

class TextPaneScrollPane extends JScrollPane implements TreeCellRenderer {
    JTextPane textPane;
    Color backgroundNonSelectionColor;
    Color backgroundSelectionColor;
    Color textNonSelectionColor;
    Color textSelectionColor;

    public TextPaneScrollPane() {
        textPane = new JTextPane();
        getViewport().add(textPane);
    }

    public void setBackgroundNonSelectionColor(Color color) {
        this.backgroundNonSelectionColor = color;
    }

    public void setBackgroundSelectionColor(Color color) {
        this.backgroundSelectionColor = color;
    }

    public void setTextNonSelectionColor(Color color) {
        this.textNonSelectionColor = color;
    }

    public void setTextSelectionColor(Color color) {
        this.textSelectionColor = color;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (selected) {
            setForeground(textSelectionColor);
            setBackground(backgroundSelectionColor);
            textPane.setForeground(textSelectionColor);
            textPane.setBackground(backgroundSelectionColor);
        } else {
            setForeground(textNonSelectionColor);
            setBackground(backgroundNonSelectionColor);
            textPane.setForeground(textNonSelectionColor);
            textPane.setBackground(backgroundNonSelectionColor);
        }

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object object = node.getUserObject();
        if (object != null && object instanceof DecoratedText) {
            textPane.setText("");
            Document doc = textPane.getStyledDocument();
            DecoratedText decText = (DecoratedText) object;
            try {
                doc.insertString(doc.getLength(), decText.getText(), getAttributeSet(decText));
            } catch (BadLocationException ex) {
                Logger.getLogger(TextPaneScrollPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (object != null && object instanceof DecoratedText[]) {
            textPane.setText("");
            Document doc = textPane.getStyledDocument();
            DecoratedText[] arrayDecText = (DecoratedText[]) object;
            for (DecoratedText decText : arrayDecText) {
                try {
                    doc.insertString(doc.getLength(), decText.getText(), getAttributeSet(decText));
                } catch (BadLocationException ex) {
                    Logger.getLogger(TextPaneScrollPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            //textPane = new JTextPane(); // Needed because take the last StyleConstants used!
            //textPane.setText(""+object);
            //return new JLabel(object.toString());
            return new DefaultTreeCellRenderer().getTreeCellRendererComponent(tree,
                    value, leaf, expanded, leaf, row, hasFocus);
        }
        return textPane;
    }

    private SimpleAttributeSet getAttributeSet(DecoratedText decoratedText) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        if (decoratedText != null) {
            if (decoratedText.getText() != null) {
            }
            if (decoratedText.getBackground() != null) {
                StyleConstants.setBackground(attributeSet, decoratedText.getBackground());
            }
            if (decoratedText.getForeground() != null) {
                StyleConstants.setForeground(attributeSet, decoratedText.getForeground());
            }
            if (decoratedText.getFont() != null) {
                StyleConstants.setFontFamily(attributeSet, decoratedText.getFont().getFamily());
                StyleConstants.setItalic(attributeSet, decoratedText.getFont().isItalic());
                StyleConstants.setBold(attributeSet, decoratedText.getFont().isBold());
                StyleConstants.setFontSize(attributeSet, decoratedText.getFont().getSize());
            }
        }
        return attributeSet;
    }
}