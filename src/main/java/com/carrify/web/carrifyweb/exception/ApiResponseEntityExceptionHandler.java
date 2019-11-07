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
}
