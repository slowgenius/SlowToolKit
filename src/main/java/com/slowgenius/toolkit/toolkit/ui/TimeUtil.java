package com.slowgenius.toolkit.toolkit.ui;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.slowgenius.toolkit.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * TODO
 *
 * @author hty
 * date: 2023/1/3
 */
public class TimeUtil {

    private final Project project;

    private JPanel main;

    private JTextField now;
    private JTextField timestamp;

    private JTextField timeStr;

    private JButton timeToStr;

    private JButton strToTime;

    public TimeUtil(Project project) {
        this.project = project;
        //刷新当前时间
        Timer timer = new Timer(1, event -> {
            long timestampVal = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
            String timestampStr = String.valueOf(timestampVal);
            now.setText(timestampStr);
            if (StringUtils.isBlank(timestamp.getText()) || (Long.parseLong(timestamp.getText()) + 1) == timestampVal) {
                timestamp.setText(timestampStr);
            }
        });
        timer.start();

        timeToStr.addActionListener(action -> {
            if (StringUtils.isBlank(timestamp.getText())) {
                Notifications.Bus.notify(new Notification("Print", "Convert failed", "Please fill in the timestamp", NotificationType.INFORMATION), project);
                return;
            }
            LocalDateTime dateTime = DateUtil.parse(Long.parseLong(timestamp.getText()));
            timeStr.setText(DateUtil.toDateStr(dateTime));
        });

        strToTime.addActionListener(action -> {
            if (StringUtils.isBlank(timeStr.getText())) {
                return;
            }
            LocalDateTime dateTime = DateUtil.parse(timeStr.getText());
            timestamp.setText(DateUtil.toEpochSecond(dateTime).toString());
        });
    }

    public JPanel getMain() {
        return main;
    }
}
