package com.carrify.web.carrifyweb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiBadRequestException extends ApiException {

    public ApiBadRequestException(String msg, String code) {
        super(msg, code);
    }
}
