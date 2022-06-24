package template;

import mp.lylb.common.code.ResultCode;

public enum ErrorCodeEnum implements ResultCode {

    ;

    private String code;

    private String info;

    ErrorCodeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getInfo() {
        return info;
    }
}
