package com.slowgenius.toolkit.utils;

import java.util.Random;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/10/18 22:36:01
 */

public class RandomUtils {

    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static Integer randomInteger(int startInclusive, int endExclusive) {
        return startInclusive == endExclusive ? startInclusive : startInclusive + new Random().nextInt(endExclusive - startInclusive);
    }

    public static char randomChar() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int number = random.nextInt(62);
        return str.charAt(number);
    }

}
