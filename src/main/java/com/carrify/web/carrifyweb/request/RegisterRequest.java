package com.carrify.web.carrifyweb.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @Size(min = 5, max = 40)
    private String username;

    @NotBlank
    @Size(min = 8, max = 60)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phone;

    @NotBlank
    @Size(min = 11, max = 11)
    private String personalNumber;

}
