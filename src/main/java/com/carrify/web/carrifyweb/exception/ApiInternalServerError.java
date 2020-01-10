package com.carrify.web.carrifyweb.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiInternalServerError extends ApiException {

    public ApiInternalServerError(String msg, String code) {
        super(msg, code);
    }
}
