package com.carrify.web.carrifyweb.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {

    private String userId;
    private String token;
    private String action;
}
