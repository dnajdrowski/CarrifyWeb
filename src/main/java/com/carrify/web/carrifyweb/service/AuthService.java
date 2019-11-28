package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiErrorConstants;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiUnauthorizedException;
import com.carrify.web.carrifyweb.model.Role.Role;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.repository.RoleRepository;
import com.carrify.web.carrifyweb.repository.UserRepository;
import com.carrify.web.carrifyweb.request.AuthRequest;
import com.carrify.web.carrifyweb.response.AuthResponse;
import com.carrify.web.carrifyweb.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Integer verifyJwtAccessToken(String token) {
        if (token != null && jwtTokenProvider.validateToken(token, true)) {
            return jwtTokenProvider.getUserIdFromJWT(token);
        } else {
            throw new ApiUnauthorizedException(CARRIFY002_MSG, CARRIFY002_CODE);
        }
    }

    public AuthResponse phoneNumberVerification(AuthRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            return AuthResponse.builder()
                    .action("Po23cVe")
                    .build();
        } else {
            return AuthResponse.builder()
                    .action("WE3ceg6")
                    .build();
        }
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new ApiUnauthorizedException(CARRIFY010_MSG, CARRIFY010_CODE);
        }

        String jwt = jwtTokenProvider.generateToken(authentication);
        return AuthResponse.builder()
                .action("a7Cg8xc")
                .token(jwt)
                .userId(String.valueOf(jwtTokenProvider.getUserIdFromJWT(jwt)))
                .build();
    }

    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiBadRequestException(ApiErrorConstants.CARRIFY005_MSG, ApiErrorConstants.CARRIFY005_CODE);
        }

        if (userRepository.existsByPersonalNumber(request.getPersonalNumber())) {
            throw new ApiBadRequestException(ApiErrorConstants.CARRIFY007_MSG, ApiErrorConstants.CARRIFY007_CODE);
        }

        User user = User.builder()
                .username(request.getPhone())
                .password(request.getPassword())
                .personalNumber(request.getPersonalNumber())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        Optional<Role> role = roleRepository.findByName("USER");
        role.ifPresent(user::setRole);

        User savedUser = userRepository.save(user);
        if (savedUser == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        return AuthResponse.builder().build();

    }

    public void validatePhoneVerificationRequest(BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (CARRIFY907_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY907_MSG, CARRIFY907_CODE);
                }
            }
        }
    }

    public void validateVerifyTokenRequest(BindingResult results) {
        if (results.hasErrors()) {
            throw new ApiBadRequestException(ApiErrorConstants.CARRIFY003_MSG, ApiErrorConstants.CARRIFY003_CODE);
        }
    }

    public void validateLoginRequest(BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (CARRIFY907_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY907_MSG, CARRIFY907_CODE);
                } else if (CARRIFY904_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY904_MSG, CARRIFY904_CODE);
                }
            }
        }
    }

    public void validateRegisterRequest(BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (error.getDefaultMessage() != null) {
                    String defaultMessage = error.getDefaultMessage();
                    if (CARRIFY901_CODE.equals(defaultMessage)) {
                        throw new ApiBadRequestException(CARRIFY901_MSG, CARRIFY901_CODE);
                    } else if (CARRIFY902_CODE.equals(defaultMessage)) {
                        throw new ApiBadRequestException(CARRIFY902_MSG, CARRIFY902_CODE);
                    } else if (CARRIFY903_CODE.equals(defaultMessage)) {
                        throw new ApiBadRequestException(CARRIFY903_MSG, CARRIFY903_CODE);
                    } else if (CARRIFY904_CODE.equals(defaultMessage)) {
                        throw new ApiBadRequestException(CARRIFY904_MSG, CARRIFY904_CODE);
                    } else if (CARRIFY905_CODE.equals(defaultMessage)) {
                        throw new ApiBadRequestException(CARRIFY905_MSG, CARRIFY905_CODE);
                    } else if (CARRIFY906_CODE.equals(defaultMessage)) {
                        throw new ApiBadRequestException(CARRIFY906_MSG, CARRIFY906_CODE);
                    } else if (CARRIFY907_CODE.equals(defaultMessage)) {
                        throw new ApiBadRequestException(CARRIFY907_MSG, CARRIFY907_CODE);
                    }
                }
            }
        }
    }
}
