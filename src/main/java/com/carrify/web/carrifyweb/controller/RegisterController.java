package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.repository.Role.Role;
import com.carrify.web.carrifyweb.repository.User.User;
import com.carrify.web.carrifyweb.request.RegisterRequest;
import com.carrify.web.carrifyweb.response.ApiResponseConstants;
import com.carrify.web.carrifyweb.service.RoleService;
import com.carrify.web.carrifyweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping({"", "/"})
    public ResponseEntity registerUser(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiBadRequestException(ApiResponseConstants.CARRIFY002_MSG, ApiResponseConstants.CARRIFY002_CODE);
        }

        if (userService.existsUserWithUsername(registerRequest.getUsername())) {
            throw new ApiBadRequestException(ApiResponseConstants.CARRIFY004_MSG, ApiResponseConstants.CARRIFY004_CODE);
        }

        if (userService.existsUserWithEmail(registerRequest.getEmail())) {
            throw new ApiBadRequestException(ApiResponseConstants.CARRIFY005_MSG, ApiResponseConstants.CARRIFY005_CODE);
        }

        if (userService.existsUserWithPhoneNumber(registerRequest.getPhone())) {
            throw new ApiBadRequestException(ApiResponseConstants.CARRIFY006_MSG, ApiResponseConstants.CARRIFY006_CODE);
        }

        if (userService.existsUserWithPersonalNumber(registerRequest.getPersonalNumber())) {
            throw new ApiBadRequestException(ApiResponseConstants.CARRIFY007_MSG, ApiResponseConstants.CARRIFY007_CODE);
        }

        User user = new User(registerRequest.getUsername(), registerRequest.getPassword(),
                registerRequest.getPersonalNumber(), registerRequest.getEmail(), registerRequest.getPhone());

        String password = passwordEncoder.encode(user.getPassword());
        log.info(password);
        user.setPassword(password);

        Optional<Role> role = roleService.findRoleByName("USER");
        role.ifPresent(user::setRole);

        User savedUser = userService.saveUser(user);
        if(savedUser != null) {
            return ResponseEntity.ok().build();
        }

        return null;
    }
}
