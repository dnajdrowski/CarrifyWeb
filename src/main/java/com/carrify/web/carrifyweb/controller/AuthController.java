package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiErrorConstants;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiUnauthorizedException;
import com.carrify.web.carrifyweb.repository.Role.Role;
import com.carrify.web.carrifyweb.repository.User.User;
import com.carrify.web.carrifyweb.request.AuthRequest;
import com.carrify.web.carrifyweb.request.JwtVerifyTokenRequest;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import com.carrify.web.carrifyweb.response.AuthResponse;
import com.carrify.web.carrifyweb.security.JwtTokenProvider;
import com.carrify.web.carrifyweb.service.RoleService;
import com.carrify.web.carrifyweb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@Api(tags = "Authorization")
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


    @ApiOperation(value = "Authenticate users", response = AuthResponse.class,
            produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Errors:\ncode: " + CARRIFY005_CODE + "\n" + "msg: " + CARRIFY005_MSG + "\n\n"
                    + "code: " + CARRIFY007_CODE + "\n" + "msg: " + CARRIFY007_MSG + "\n"
                    + "code: " + CARRIFY901_CODE + "\n" + "msg: " + CARRIFY901_MSG + "\n"
                    + "code: " + CARRIFY903_CODE + "\n" + "msg: " + CARRIFY903_MSG + "\n"
                    + "code: " + CARRIFY904_CODE + "\n" + "msg: " + CARRIFY904_MSG + "\n"
                    + "code: " + CARRIFY905_CODE + "\n" + "msg: " + CARRIFY905_MSG + "\n"
                    + "code: " + CARRIFY906_CODE + "\n" + "msg: " + CARRIFY906_MSG + "\n"
                    + "code: " + CARRIFY907_CODE + "\n" + "msg: " + CARRIFY907_MSG + "\n"
                    + "code: " + CARRIFY908_CODE + "\n" + "msg: " + CARRIFY908_MSG, response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "Errors:\ncode: " + CARRIFY_INTERNAL_CODE + "\n" + "msg: " + CARRIFY_INTERNAL_MSG,
                    response = ApiErrorResponse.class)
    })

    @PostMapping()
    public ResponseEntity auth(@Valid @RequestBody AuthRequest request, BindingResult results) {
        if(request.getAction() == null ){
            request.setAction("");
        }
        switch (request.getAction()) {
            case "H421sCa":
                return performPhoneNumberVerificationAction(request, results);
            case "Po23cVe":
                return performLoginAction(request, results);
            case "WE3ceg6":
                return performRegisterAction(request, results);
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
    public ResponseEntity verifyToken(@Valid @RequestBody JwtVerifyTokenRequest verifyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiBadRequestException(ApiErrorConstants.CARRIFY003_MSG, ApiErrorConstants.CARRIFY003_CODE);
        }
        String token = verifyRequest.getAccessToken();
        if (token != null && jwtTokenProvider.validateToken(token, true)) {
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
                    throw new ApiBadRequestException(CARRIFY907_MSG, CARRIFY907_CODE);
                }
            }
        }
        AuthResponse authResponse = new AuthResponse();
        if (userService.existsUserWithPhoneNumber(request.getPhone())) {
            authResponse.setAction("Po23cVe");
            return ResponseEntity.ok(authResponse);
        } else {
            authResponse.setAction("WE3ceg6");
            return ResponseEntity.ok(authResponse);
        }
    }

    private ResponseEntity performLoginAction(AuthRequest request, BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (CARRIFY907_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY907_MSG, CARRIFY907_CODE);
                } else if (CARRIFY904_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY904_MSG, CARRIFY904_CODE);
                }
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAction("a7Cg8xc");
        authResponse.setToken(jwt);
        authResponse.setUserId(String.valueOf(jwtTokenProvider.getUserIdFromJWT(jwt)));
        return ResponseEntity.ok(authResponse);
    }

    private ResponseEntity performRegisterAction(AuthRequest request, BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (error.getDefaultMessage() != null) {
                    switch (error.getDefaultMessage()) {
                        case CARRIFY901_CODE:
                            throw new ApiBadRequestException(CARRIFY901_MSG, CARRIFY901_CODE);
                        case CARRIFY902_CODE:
                            throw new ApiBadRequestException(CARRIFY902_MSG, CARRIFY902_CODE);
                        case CARRIFY903_CODE:
                            throw new ApiBadRequestException(CARRIFY903_MSG, CARRIFY903_CODE);
                        case CARRIFY904_CODE:
                            throw new ApiBadRequestException(CARRIFY904_MSG, CARRIFY904_CODE);
                        case CARRIFY905_CODE:
                            throw new ApiBadRequestException(CARRIFY905_MSG, CARRIFY905_CODE);
                        case CARRIFY906_CODE:
                            throw new ApiBadRequestException(CARRIFY906_MSG, CARRIFY906_CODE);
                        case CARRIFY907_CODE:
                            throw new ApiBadRequestException(CARRIFY907_MSG, CARRIFY907_CODE);
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
        if (savedUser != null) {
            return ResponseEntity.ok(new AuthResponse());
        } else {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }
    }
}
