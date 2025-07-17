package com.example.Unit_2_Project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Or load from config

    // Generate a JWT for a user
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key)
                .compact();
    }

    // method to validate token
    public boolean validateToken(String token, String email) {
        String subject = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
        return subject.equals(email);
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}


