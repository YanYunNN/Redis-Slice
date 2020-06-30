package com.yanyun.redis.model;

import lombok.Data;

/**
 * @author xcai
 */
@Data
public class RestResponse<RT> {
    private int code;
    private String message;
    private RT result;

    public RestResponse() {
    }

    public RestResponse(int code, String message, RT result) {
        this.setCode(code);
        this.setMessage(message);
        this.setResult(result);
    }

    public static <T> RestResponse<T> good(T result) {
        return result(0, "OK", result);
    }

    public static <T> RestResponse<T> result(int code, String message, T result) {
        return new RestResponse(code, message, result);
    }

    public static <T> RestResponse<T> bad(int code, String message, T result) {
        return result(code, message, result);
    }

    public static <T> RestResponse<T> bad(int code, String message) {
        return result(code, message, null);
    }
}

