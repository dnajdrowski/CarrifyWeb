package com.carrify.web.carrifyweb.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String userId;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
