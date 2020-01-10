package com.carrify.web.carrifyweb.request;


import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;

@Getter
@Setter
public class AuthRequest {

    @NotEmpty(message = CARRIFY901_CODE)
    private String action;

    @NotBlank(message = CARRIFY904_CODE)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+!=])(?=\\S+$).{8,}$",
            message = CARRIFY904_CODE)
    private String password;

    @NotBlank(message = CARRIFY905_CODE)
    @Pattern(regexp = "[0-9]{11}", message = CARRIFY905_CODE)
    private String personalNumber;

    @NotBlank(message = CARRIFY906_CODE)
    @Email(message = CARRIFY906_CODE)
    private String email;

    @NotBlank(message = CARRIFY907_CODE)
    @Pattern(regexp = "[0-9]{9}", message = CARRIFY907_CODE)
    private String phone;
}
