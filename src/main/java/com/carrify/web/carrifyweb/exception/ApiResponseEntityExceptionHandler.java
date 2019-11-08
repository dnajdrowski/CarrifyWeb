package com.carrify.web.carrifyweb.exception;

import com.carrify.web.carrifyweb.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiResponseEntityExceptionHandler {

    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<ApiResponse> resolveNotFoundException(ApiNotFoundException e) {
        ApiResponse response = new ApiResponse(e.getCode(), e.getMsg());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ApiBadRequestException.class)
    public ResponseEntity<ApiResponse> resolveBadRequestException(ApiBadRequestException e) {
        ApiResponse response = new ApiResponse(e.getCode(), e.getMsg());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ApiUnauthorizedException.class)
    public ResponseEntity<ApiResponse> resolveUnauthorizedException(ApiUnauthorizedException e) {
        ApiResponse response = new ApiResponse(e.getCode(), e.getMsg());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
