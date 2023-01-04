package com.slowgenius.toolkit.utils;

import com.alibaba.fastjson.JSONPath;

/**
 * json工具类
 *
 * @author hty
 * date: 2023/1/3
 */
public class JSONUtils {

    public static Object read(String json, String path) {
        return JSONPath.read(json, path);
    }

}
