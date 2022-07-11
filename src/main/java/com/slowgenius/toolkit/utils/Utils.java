package com.slowgenius.toolkit.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class Utils {

    /**
     * 获取剪切板数据，也就是 Ctrl + C
     */
    public static String getSystemClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);

        if (null == clipTf) {
            return ret;
        }

        // 检查内容是否是文本类型
        if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public static String getWordStartOffset(CharSequence editorText, int cursorOffset) {
        if (editorText.length() == 0) {
            return "";
        }
        if (!Character.isJavaIdentifierPart(editorText.charAt(cursorOffset))) {
            int start = 0;
            // 定位开始位置
            while (!Character.isJavaIdentifierPart(editorText.charAt(cursorOffset))) {
                start++;
                cursorOffset++;
            }
            return " ".repeat(start);
        }
        return "";
    }


}
