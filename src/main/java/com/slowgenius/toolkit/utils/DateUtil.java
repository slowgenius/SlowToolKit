package com.slowgenius.toolkit.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 时间解析工具类
 *
 * @author hty
 * date: 2023/1/3
 */
public class DateUtil {


    public static LocalDateTime parse(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime parse(Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
    }

    public static LocalDateTime parse(String date, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public static Long toEpochSecond(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return localDateTime.toEpochSecond(zoneOffset);
    }

    public static Long toEpochSecond(LocalDateTime localDateTime) {
        return toEpochSecond(localDateTime, ZoneOffset.ofHours(8));
    }

    public static String toDateStr(LocalDateTime localDateTime) {
        return localDateTime.toString().replace("T", " ");
    }




}
