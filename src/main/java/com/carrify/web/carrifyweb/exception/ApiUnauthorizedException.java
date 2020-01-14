package com.carrify.web.carrifyweb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiUnauthorizedException extends ApiException {

    public ApiUnauthorizedException(String msg, String code) {
        super(msg, code);
    }
}
