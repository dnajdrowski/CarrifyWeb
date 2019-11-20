package com.carrify.web.carrifyweb.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String userId;
    private String token;
    private String action;
}
