package com.github.donnyk22.project.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.github.donnyk22.project.models.dtos.AppProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String SECRET;
    private final long EXPIRATION;

    public JwtUtil(AppProperties appProperties){
        this.SECRET = appProperties.getSecret();
        this.EXPIRATION = appProperties.getTtlMinutes() * 60 * 1000;
    }

    public String generateToken(Integer id, String name, String email, String role) {
        return Jwts.builder()
            .setSubject(id.toString())
            .claim("name", name)
            .claim("email", email)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
            .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}