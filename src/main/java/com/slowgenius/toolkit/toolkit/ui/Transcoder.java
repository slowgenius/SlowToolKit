package com.slowgenius.toolkit.toolkit.ui;

import com.slowgenius.toolkit.utils.TransCodeUtil;

import javax.swing.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2023/1/18 16:56:20
 */

public class Transcoder {
    private JPanel main;
    private JButton encodeButton;
    private JTextArea input;
    private JTextArea output;

    private JButton md5Button;
    private JButton urlEncode;

    private JButton base64;
    private JButton unicodeButton;
    private JButton decodeButton;

    private Model model = Model.MD5;

    public Transcoder() {
        //md5编码监听事件
        md5Button.addActionListener(action -> {
            model = Model.MD5;
        });
        //url编码监听事件
        urlEncode.addActionListener(action -> {
            model = Model.URL_ENCODE;
        });
        //base64编码监听事件
        base64.addActionListener(action -> {
            model = Model.BASE64;
        });
        //unicode
        unicodeButton.addActionListener(action -> {
            model = Model.UNICODE;
        });
        encodeButton.addActionListener(action -> {
            String text = input.getText();
            String result = "";
            if (model == Model.MD5) {
                result = "32位: " + TransCodeUtil.md5(text) + "\n";
                result += "32位大写: " + TransCodeUtil.md5UpperCase(text) + "\n";
                result += "16位: " + TransCodeUtil.md5(text).substring(8, 24) + "\n";
                result += "16位大写: " + TransCodeUtil.md5(text).substring(8, 24).toUpperCase();
            }
            if (model == Model.URL_ENCODE) {
                result = URLEncoder.encode(text, StandardCharsets.UTF_8);
            }
            if (model == Model.BASE64) {
                result = new String(Base64.getEncoder().encode(text.getBytes()));
            }
            if (model == Model.UNICODE) {
                result = TransCodeUtil.stringToUnicode(text);
            }
            output.setText(result);
        });

        decodeButton.addActionListener(action -> {
            String text = output.getText();
            String result = "";
            if (model == Model.MD5) {
                result = "不支持md5解码";
            }
            if (model == Model.URL_ENCODE) {
                result = URLDecoder.decode(text, StandardCharsets.UTF_8);
            }
            if (model == Model.BASE64) {
                result = new String(Base64.getDecoder().decode(text.getBytes()));
            }
            if (model == Model.UNICODE) {
                result = TransCodeUtil.unicodeToString(text);
            }
            input.setText(result);
        });
    }


    public JPanel getMain() {
        return this.main;
    }

    private void createUIComponents() {

    }

    public enum Model {
        MD5, URL_ENCODE, BASE64, UNICODE
    }


}
