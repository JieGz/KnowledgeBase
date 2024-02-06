package com.knowledge.base.enums;

/**
 * @author jieguangzhi
 * @date 2024-01-30
 */
public enum CodeEnum {
    SUCCESS(1000, "请求成功"),
    FAIL(2000, "请求失败");
    public final int code;
    public final String msg;

    public Integer getCode() {
        return this.code;
    }

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
