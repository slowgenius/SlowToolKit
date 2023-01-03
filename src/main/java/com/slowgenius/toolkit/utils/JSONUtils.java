package com.slowgenius.toolkit.utils;

import com.alibaba.fastjson.JSONPath;

/**
 * json工具类
 *
 * @author hty
 * date: 2023/1/3
 */
public class JSONUtils {

    public static <T> T read(String json, String path, Class<T> clazz) {
        return JSONPath.read(json, path, clazz);
    }

}
