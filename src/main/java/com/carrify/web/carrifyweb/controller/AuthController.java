package com.carrify.web.carrifyweb.controller;


import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.request.AuthRequest;
import com.carrify.web.carrifyweb.request.JwtVerifyTokenRequest;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import com.carrify.web.carrifyweb.response.AuthResponse;
import com.carrify.web.carrifyweb.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@Api(tags = "Authorization")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @ApiOperation(value = "Authenticate users", response = AuthResponse.class,
            produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK\nResponses:\n action: H421sCa - check if phone number exists in db\n"
                    + "action: Po23cVe - login perform action\naction: WE3ceg6 - register perform action\n"
                    + "action: a7Cg8xc - successfully login response"),
            @ApiResponse(code = 400, message = "Errors:\ncode: " + CARRIFY005_CODE + "\n" + "msg: " + CARRIFY005_MSG + "\n"
                    + "code: " + CARRIFY007_CODE + "\n" + "msg: " + CARRIFY007_MSG + "\n"
                    + "code: " + CARRIFY901_CODE + "\n" + "msg: " + CARRIFY901_MSG + "\n"
                    + "code: " + CARRIFY903_CODE + "\n" + "msg: " + CARRIFY903_MSG + "\n"
                    + "code: " + CARRIFY904_CODE + "\n" + "msg: " + CARRIFY904_MSG + "\n"
                    + "code: " + CARRIFY905_CODE + "\n" + "msg: " + CARRIFY905_MSG + "\n"
                    + "code: " + CARRIFY906_CODE + "\n" + "msg: " + CARRIFY906_MSG + "\n"
                    + "code: " + CARRIFY907_CODE + "\n" + "msg: " + CARRIFY907_MSG + "\n"
                    + "code: " + CARRIFY908_CODE + "\n" + "msg: " + CARRIFY908_MSG, response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Errors:\ncode: " + CARRIFY_INTERNAL_CODE + "\n" + "msg: " + CARRIFY_INTERNAL_MSG,
                    response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "Errors:\ncode: " + CARRIFY010_CODE + "\n" + "msg: " + CARRIFY010_MSG)
    })

    @PostMapping()
    public ResponseEntity auth(@Valid @RequestBody AuthRequest request, BindingResult results) {
        if (request.getAction() == null) {
            request.setAction("");
        }
        switch (request.getAction()) {
            case "H421sCa":
                authService.validatePhoneVerificationRequest(results);
                return ResponseEntity.ok().body(authService.phoneNumberVerification(request));
            case "Po23cVe":
                authService.validateLoginRequest(results);
                return ResponseEntity.ok().body(authService.login(request));
            case "WE3ceg6":
                authService.validateRegisterRequest(results);
                return ResponseEntity.ok(authService.register(request));
            default:
                throw new ApiBadRequestException(CARRIFY908_MSG, CARRIFY908_CODE);
        }
    }

    @ApiOperation(value = "Verify authorization token", response = Integer.class, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Errors:\ncode: " + CARRIFY002_CODE + "\n" + "msg: " + CARRIFY002_MSG,
                    response = ApiErrorResponse.class),
            @ApiResponse(code = 400, message = "Errors:\ncode: " + CARRIFY003_CODE + "\n" + "msg: " + CARRIFY003_MSG,
                    response = ApiErrorResponse.class)
    })
    @PostMapping("/verifyToken")
    public ResponseEntity verifyToken(@Valid @RequestBody JwtVerifyTokenRequest verifyRequest, BindingResult results) {
        authService.validateVerifyTokenRequest(results);
        return ResponseEntity.ok().body(authService.verifyJwtAccessToken(verifyRequest.getAccessToken()));
    }

}
