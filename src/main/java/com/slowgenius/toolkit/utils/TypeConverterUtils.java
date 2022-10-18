package com.slowgenius.toolkit.utils;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/19 20:53:41
 */

public class TypeConverterUtils {


    public static String convert(String sqlType) {
        if (sqlType.contains("varchar") || sqlType.contains("char") || sqlType.contains("text") || sqlType.contains("json")) {
            return "String";
        }
        if (sqlType.contains("bigint") || sqlType.contains("int8")) {
            return "Long";
        }
        if (sqlType.contains("decimal")) {
            return "BigDecimal";
        }
        if (sqlType.contains("int")) {
            return "Integer";
        }
        if (sqlType.contains("date") || sqlType.contains("datetime") || sqlType.contains("timestamp")) {
            return "Date";
        }
        if (sqlType.contains("boolean")) {
            return "Boolean";
        }
        return null;
    }


}
