package com.carrify.web.carrifyweb.exception;


import lombok.Getter;

@Getter
class ApiException extends RuntimeException {

    private final String msg;
    private final String code;

    ApiException(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

}
