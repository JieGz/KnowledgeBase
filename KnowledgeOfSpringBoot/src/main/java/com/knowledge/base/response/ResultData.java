package com.knowledge.base.response;

import com.knowledge.base.enums.CodeEnum;
import lombok.Data;

/**
 * @author jieguangzhi
 * @date 2024-01-30
 */
@Data
public class ResultData<T> {
    private Integer code;
    private String message;
    private T data;

    public ResultData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultData success(CodeEnum codeEnum) {
        return new ResultData(codeEnum.code, codeEnum.msg);
    }

    public static ResultData success(String msg) {
        return new ResultData(CodeEnum.SUCCESS.getCode(), msg);
    }
}