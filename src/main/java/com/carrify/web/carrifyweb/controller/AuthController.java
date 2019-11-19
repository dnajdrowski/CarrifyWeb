package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiErrorConstants;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiUnauthorizedException;
import com.carrify.web.carrifyweb.repository.Role.Role;
import com.carrify.web.carrifyweb.repository.User.User;
import com.carrify.web.carrifyweb.request.AuthRequest;
import com.carrify.web.carrifyweb.request.JwtVerifyTokenRequest;
import com.carrify.web.carrifyweb.response.AuthResponse;
import com.carrify.web.carrifyweb.response.JwtAuthenticationResponse;
import com.carrify.web.carrifyweb.security.JwtTokenProvider;
import com.carrify.web.carrifyweb.service.RoleService;
import com.carrify.web.carrifyweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping({"", "/"})
    public ResponseEntity auth(@Valid @RequestBody AuthRequest request, BindingResult results) {
        switch (request.getAction()) {
            case "H421sCa":
                return performPhoneNumberVerificationAction(request, results);
            case "Po23cVe":
                return performLoginAction(request, results);
            case "WE3ceg6":
                return performRegisterAction(request, results);
            default:
                throw new ApiBadRequestException(CARRIFY908_MESSAGE, CARRIFY908_CODE);
        }
    }

    @PostMapping("/verifyToken")
    public ResponseEntity verifyToken(@Valid @RequestBody JwtVerifyTokenRequest verifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiBadRequestException(ApiErrorConstants.CARRIFY003_MSG, ApiErrorConstants.CARRIFY003_CODE);
        }
        String token = verifyRequest.getAccessToken();
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Integer userId = jwtTokenProvider.getUserIdFromJWT(token);
            return ResponseEntity.ok(userId);
        } else {
            throw new ApiUnauthorizedException(CARRIFY002_MSG, CARRIFY002_CODE);
        }
    }

    private ResponseEntity performPhoneNumberVerificationAction(AuthRequest request, BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (CARRIFY907_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY907_MESSAGE, CARRIFY907_CODE);
                }
            }
        }
        AuthResponse authResponse = new AuthResponse();
        if (userService.existsUserWithPhoneNumber(request.getPhone())) {
            authResponse.setAction("Po23cVe");
            authResponse.setPhoneNumber(request.getPhone());
            return ResponseEntity.ok(authResponse);
        } else {
            authResponse.setAction("WE3ceg6");
            authResponse.setPhoneNumber(request.getPhone());
            return ResponseEntity.ok(authResponse);
        }
    }

    private ResponseEntity performLoginAction(AuthRequest request, BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (CARRIFY906_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY906_MESSAGE, CARRIFY906_CODE);
                } else if (CARRIFY904_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY904_MESSAGE, CARRIFY904_CODE);
                }
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,
                String.valueOf(jwtTokenProvider.getUserIdFromJWT(jwt))));
    }

    private ResponseEntity performRegisterAction(AuthRequest request, BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (error.getDefaultMessage() != null) {
                    switch (error.getDefaultMessage()) {
                        case CARRIFY901_CODE:
                            throw new ApiBadRequestException(CARRIFY901_MESSAGE, CARRIFY901_CODE);
                        case CARRIFY902_CODE:
                            throw new ApiBadRequestException(CARRIFY902_MESSAGE, CARRIFY902_CODE);
                        case CARRIFY903_CODE:
                            throw new ApiBadRequestException(CARRIFY903_MESSAGE, CARRIFY903_CODE);
                        case CARRIFY904_CODE:
                            throw new ApiBadRequestException(CARRIFY904_MESSAGE, CARRIFY904_CODE);
                        case CARRIFY905_CODE:
                            throw new ApiBadRequestException(CARRIFY905_MESSAGE, CARRIFY905_CODE);
                        case CARRIFY906_CODE:
                            throw new ApiBadRequestException(CARRIFY906_MESSAGE, CARRIFY906_CODE);
                        case CARRIFY907_CODE:
                            throw new ApiBadRequestException(CARRIFY907_MESSAGE, CARRIFY907_CODE);
                        default:
                            throw new ApiBadRequestException("", "");
                    }
                }
            }
        }

        if (userService.existsUserWithEmail(request.getEmail())) {
            throw new ApiBadRequestException(ApiErrorConstants.CARRIFY005_MSG, ApiErrorConstants.CARRIFY005_CODE);
        }

        if (userService.existsUserWithPersonalNumber(request.getPersonalNumber())) {
            throw new ApiBadRequestException(ApiErrorConstants.CARRIFY007_MSG, ApiErrorConstants.CARRIFY007_CODE);
        }

        User user = new User(request.getPhone(), request.getPassword(),
                request.getPersonalNumber(), request.getEmail(), request.getPhone());

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        Optional<Role> role = roleService.findRoleByName("USER");
        role.ifPresent(user::setRole);

        User savedUser = userService.saveUser(user);
        if(savedUser != null) {
            return ResponseEntity.ok().build();
        } else {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }
    }
}
