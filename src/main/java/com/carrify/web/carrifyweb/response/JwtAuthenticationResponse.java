package com.carrify.web.carrifyweb.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String userId;

    public JwtAuthenticationResponse(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
