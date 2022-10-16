package com.slowgenius.toolkit.toolkit.data;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @author slowgenius
 * @version tools
 * @since 2022/6/24 22:15:30
 */

public class LanguageData {

    private String error_code;

    private String error_msg;

    private ResultData data;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public ResultData getData() {
        return data;
    }

    public void setData(ResultData data) {
        this.data = data;
    }

    public static class ResultData {

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String src;

        @Override
        public String toString() {
            return "ResultData{" +
                    "src='" + src + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LanguageData{" +
                "error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static String getLanguage(String name) {
        String appid = "20220624001256138";
        String salt = "seomthing";
        String secret = "T8fA8V727LzPcJJaBYix";
        String sign = DigestUtil.md5Hex(appid + name + salt + secret);
        String url = "https://fanyi-api.baidu.com/api/trans/vip/language?q=" + name + "&appid=" + appid + "&salt=" + salt + "&sign=" + sign;
        String get = HttpUtil.get(url);
        LanguageData parse = JSONObject.parseObject(get, LanguageData.class);
        return parse.getData().getSrc();
    }
}

