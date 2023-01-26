package com.slowgenius.toolkit.toolkit.ui;

import com.slowgenius.toolkit.utils.PostUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2023/1/26 19:53:44
 */

public class EasyRequest {
    private JTextField url;
    private JTextArea request;
    private JTextArea result;
    private JComboBox<String> type;
    private JButton sendButton;
    private JPanel main;


    public EasyRequest() {
        sendButton.addActionListener(e -> {
            try {
                String requestBody = type.getSelectedItem().toString();
                if (requestBody.equals("POST")) {
                    String requestResult = PostUtils.post(url.getText(), request.getText());
                    result.setText(requestResult);
                }
                if (requestBody.equals("GET")) {
                    String requestResult = PostUtils.get(url.getText());
                    result.setText(requestResult);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

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
                request.setMinimumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                request.setMaximumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                result.setMinimumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
                result.setMaximumSize(new Dimension(Double.valueOf(mainSize.getWidth() - 150).intValue() / 2, -1));
            }
        });
    }

    public JPanel getMain() {
        return main;
    }
}
