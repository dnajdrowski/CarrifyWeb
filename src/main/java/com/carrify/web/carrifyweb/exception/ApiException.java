package com.carrify.web.carrifyweb.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final String msg;
    private final String code;

    public ApiException(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }
}
