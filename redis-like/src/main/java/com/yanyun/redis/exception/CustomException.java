package com.yanyun.redis.exception;

import lombok.Data;

/**
 * 自定义异常类

 */
@Data
public class CustomException extends RuntimeException {
    /**
     * 错误码
     */
    private ErrorCodeEnum errorCodeEnum;

    public CustomException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "errorCodeEnum=" + errorCodeEnum +
                '}';
    }
}
