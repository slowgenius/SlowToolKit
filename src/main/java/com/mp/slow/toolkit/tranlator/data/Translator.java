package com.mp.slow.toolkit.tranlator.data;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author slowgenius
 * @version little-spring
 * @since 2022/6/24 22:42:35
 */

public class Translator {

    private String error_code;

    private String error_msg;

    private String from;

    private String to;

    public List<ResultData> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<ResultData> trans_result) {
        this.trans_result = trans_result;
    }

    private List<ResultData> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Translator{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", trans_result=" + trans_result +
                '}';
    }

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


    public static class ResultData {
        private String src;

        private String dst;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        @Override
        public String toString() {
            return "ResultData{" +
                    "src='" + src + '\'' +
                    ", dst='" + dst + '\'' +
                    '}';
        }
    }

    public static String translate(String from, String to, String text) {
        String appid = "20220624001256138";
        String salt = "seomthing";
        String secret = "T8fA8V727LzPcJJaBYix";
        String sign = DigestUtil.md5Hex(appid + text + salt + secret);
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q=" + text + "&appid=" + appid + "&salt=" + salt + "&sign=" + sign + "&from=" + from + "&to=" + to;
        String get = HttpUtil.get(url);
        get = UnicodeUtil.toString(get);
        System.out.println(get);
        Translator translator = JSONObject.parseObject(get, Translator.class);
        if (translator.getError_code() != null && !translator.getError_code().equals("52000")) {
            return translator.getError_msg();
        }
        String str = "";
        for (ResultData allWord : translator.getTrans_result()) {
            str = str + " [" + allWord.getDst() + "] ";
        }
        return str;
    }
}
