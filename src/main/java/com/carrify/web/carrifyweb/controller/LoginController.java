package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.request.JwtVerifyTokenRequest;
import com.carrify.web.carrifyweb.request.LoginRequest;
import com.carrify.web.carrifyweb.response.ApiResponseConstants;
import com.carrify.web.carrifyweb.response.JwtAuthenticationResponse;
import com.carrify.web.carrifyweb.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping({"", "/"})
    public ResponseEntity loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ApiBadRequestException(ApiResponseConstants.CARRIFY002_MSG, ApiResponseConstants.CARRIFY002_CODE);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,
                String.valueOf(tokenProvider.getUserIdFromJWT(jwt))));
    }

    @PostMapping("/verifyToken")
    public ResponseEntity verifyToken(@Valid @RequestBody JwtVerifyTokenRequest verifyRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ApiBadRequestException(ApiResponseConstants.CARRIFY003_MSG, ApiResponseConstants.CARRIFY003_CODE);
        }
        String token = verifyRequest.getAccessToken();
        if(token != null && tokenProvider.validateToken(token)) {
            Integer userId = tokenProvider.getUserIdFromJWT(token);
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
