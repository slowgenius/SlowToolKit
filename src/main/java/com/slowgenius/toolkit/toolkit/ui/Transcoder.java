package com.slowgenius.toolkit.toolkit.ui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.slowgenius.toolkit.swingUtil.InputForm;
import com.slowgenius.toolkit.utils.TransCodeUtil;

import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2023/1/18 16:56:20
 */

public class Transcoder {

    public final Project project;
    private JPanel main;
    private JPanel md5;
    private JToolBar toolbar;

    private Model model;

    private JPanel input;


    public Transcoder(Project project) {
        this.project = project;
    }


    public JPanel getMain() {
        return this.main;
    }

    private void createUIComponents() {
        md5 = new JPanel();
        md5.setBackground(JBColor.CYAN);

        Dimension parentSize = md5.getSize();
        InputForm inputForm = new InputForm("输入");
        input = inputForm.getMain();
        input.setSize(new Dimension(Double.valueOf(parentSize.getWidth()).intValue() / 2 - 100, Double.valueOf(parentSize.getHeight()).intValue()));
        input.setAlignmentY(1000);
        md5.add(input);

        Button transform = new Button();
        md5.add(transform);

        InputForm outputInform = new InputForm("输出");
        JPanel output = outputInform.getMain();
        outputInform.getText().setSize(new Dimension(Double.valueOf(parentSize.getWidth()).intValue() / 2 - 100, Double.valueOf(parentSize.getHeight()).intValue()));
        md5.add(output);


        transform.addActionListener(action -> {
            String text = inputForm.getText().getText();
            String result = "";
            if (model == Model.MD5) {
                result = TransCodeUtil.md5(text);
            }
            if (model == Model.URL_ENCODE) {
                result = URLEncoder.encode(text, StandardCharsets.UTF_8);
            }
            if (model == Model.BASE64) {
                result = new String(Base64.getDecoder().decode(text));
            }
            outputInform.setText(result);
        });

        toolbar = new JToolBar();
        //md5编码监听事件
        Button md5Button = new Button("md5编码");
        md5Button.addActionListener(action -> {
            model = Model.MD5;
        });
        toolbar.add(md5Button);
        //url编码监听事件
        Button urlEncode = new Button("url编码");
        urlEncode.addActionListener(action -> {
            model = Model.URL_ENCODE;
        });
        toolbar.add(urlEncode);
        //base64编码监听事件
        Button base64 = new Button("base64");
        base64.addActionListener(action -> {
            model = Model.BASE64;
        });
        toolbar.add(base64);
    }

    public enum Model {
        MD5, URL_ENCODE, BASE64
    }


}
