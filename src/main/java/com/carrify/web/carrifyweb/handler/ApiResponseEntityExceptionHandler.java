package com.carrify.web.carrifyweb.handler;


import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.exception.ApiUnauthorizedException;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY_INTERNAL_CODE;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY_INTERNAL_MSG;

@RestControllerAdvice
@Slf4j
public class ApiResponseEntityExceptionHandler {

    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> resolveNotFoundException(ApiNotFoundException e) {
        ApiErrorResponse response = new ApiErrorResponse(e.getCode(), e.getMsg());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ApiBadRequestException.class)
    public ResponseEntity<ApiErrorResponse> resolveBadRequestException(ApiBadRequestException e) {
        ApiErrorResponse response = new ApiErrorResponse(e.getCode(), e.getMsg());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ApiUnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> resolveUnauthorizedException(ApiUnauthorizedException e) {
        ApiErrorResponse response = new ApiErrorResponse(e.getCode(), e.getMsg());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ApiInternalServerError.class)
    public ResponseEntity<ApiErrorResponse> resolveInternalServerError(ApiInternalServerError e) {
        ApiErrorResponse response = new ApiErrorResponse(e.getMsg(), e.getCode());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
