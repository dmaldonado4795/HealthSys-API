package com.iDevWorks.HealthSys_API.common.utils;

import com.iDevWorks.HealthSys_API.infrastructure.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;
    private final int jwtExpiration;

    @Autowired
    public JwtUtil(JwtProperties jwtProperties) {
        if (jwtProperties.getSecret().length() < 32)
            throw new IllegalArgumentException("Secret key must be at least 256 bits (32 characters) long.");
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        jwtExpiration = jwtProperties.getExpiration();
    }

    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}
