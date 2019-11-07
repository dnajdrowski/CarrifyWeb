package com.carrify.web.carrifyweb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiNotFoundException extends RuntimeException{

    private final String msg;
    private final String code;

    public ApiNotFoundException(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

}
