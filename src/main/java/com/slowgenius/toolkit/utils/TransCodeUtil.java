package com.slowgenius.toolkit.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2023/1/18 17:01:49
 */

public class TransCodeUtil {

    /**
     * digest()最后确定返回md5 hash值，返回值为8位字符串。
     * 因为md5 hash值是16位的hex值，实际上就是8位的字符
     * BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
     * 一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
     */
    public static String md5(String str, String salt) {

        MessageDigest md;// 生成一个MD5加密计算摘要
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update((str + salt).getBytes());// 计算md5函数
        return new BigInteger(1, md.digest()).toString(16);
    }

    public static String md5(String str) {
        return md5(str, "");
    }

    public static String md5UpperCase(String str) {
        return md5(str).toUpperCase();
    }


    public static String unicodeToString(String str) {
        int start = 0;
        int end = 0;
        StringBuilder buffer = new StringBuilder();
        while (start > -1) {
            end = str.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = str.substring(start + 2, str.length());
            } else {
                charStr = str.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(Character.toString(letter));
            start = end;
        }
        return buffer.toString();
    }

    public static String stringToUnicode(String str) {
        char[] utfBytes = str.toCharArray();
        StringBuilder unicodeBytes = new StringBuilder();
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes.append("\\u").append(hexB);
        }
        return unicodeBytes.toString();
    }


}
