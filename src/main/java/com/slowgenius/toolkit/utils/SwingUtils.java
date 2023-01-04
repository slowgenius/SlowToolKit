package com.slowgenius.toolkit.utils;

import java.awt.*;

/**
 * swing工具类
 *
 * @author hty
 * date: 2023/1/4
 */
public class SwingUtils {

    /**
     * 获得屏幕得宽
     * screenSize.getWidth()
     * 获得屏幕得高
     * screenSize.getHeight()
     */
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

}
