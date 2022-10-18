package com.slowgenius.toolkit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/6/26 17:53:57
 */

public class StrUtils {


    /**
     * 驼峰转下划线
     *
     * @param str 目标字符串
     */
    public static String camelToUnderline(String str) {
        String regex = "([A-Z])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group();
            str = str.replaceAll(target, "_" + target.toLowerCase());
        }
        return str;
    }

    /**
     * 下划线转驼峰
     *
     * @param str 目标字符串
     */
    public static String underlineToCamel(String str) {
        String regex = "_(.)";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find()) {
            String target = matcher.group(1);
            str = str.replaceAll("_" + target, target.toUpperCase());
        }
        return str;
    }

    public static String firstLetterUpper(String str) {
        char[] cs = str.toCharArray();
        if (cs.length == 0) {
            return "";
        }
        cs[0] -= 32;
        return String.valueOf(cs);
    }


    public static String firstLetterLower(String str) {
        char[] cs = str.toCharArray();
        if (cs.length == 0) {
            return "";
        }
        cs[0] += 32;
        return String.valueOf(cs);
    }
}
