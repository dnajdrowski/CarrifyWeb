package com.carrify.web.carrifyweb.security;

import com.carrify.web.carrifyweb.repository.User.User;
import com.carrify.web.carrifyweb.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final UserService userService;

    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @Value("${jwt.token.expiration.length}")
    private Long jwtExpirationTime;

    @Value(("${jwt.token.secret}"))
    private String jwtSecret;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationTime);

        String token = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("userId", Integer.toString(userPrincipal.getId()))
                .signWith(getSigningKey())
                .compact();

        User user = userService.saveUserToken(userPrincipal.getId(), token);
        if (user != null) {
            return token;
        }
        return null;
    }

    public Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt((String) claims.get("userId"));
    }

    public boolean validateToken(String authToken, Boolean isVerifyToken) {
        try {
            if(isVerifyToken) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY, 5);
                if(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().getExpiration().before(calendar.getTime())) {
                    return false;
                }
            }
            return userService.checkIfTokenMatchesToUser(authToken,
                    Integer.parseInt((String) Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(authToken)
                            .getBody()
                            .get("userId")));
        } catch (Exception e) {
            return false;
        }
    }
}
