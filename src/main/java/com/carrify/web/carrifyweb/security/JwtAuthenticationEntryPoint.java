package com.carrify.web.carrifyweb.security;

import com.carrify.web.carrifyweb.exception.ApiErrorConstants;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        ApiErrorResponse response = new ApiErrorResponse(ApiErrorConstants.CARRIFY006_CODE, ApiErrorConstants.CARRIFY006_MSG);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        OutputStream output = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(output, response);
        output.flush();
    }
}
