package com.example.Unit_2_Project.security;

import com.example.Unit_2_Project.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Load the signing key from application.properties (jwt.secret)
    @Value("${jwt.secret}")
    private String secret;

    // Construct signing key from the secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Create a JWT for the given email
    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(email)  // Set email as the subject
                .claim("role", role.name()) // Adds "role": "USER" to JWT claims
                .setIssuedAt(new Date())                 // Token issue time
                .setExpiration(new Date(System.currentTimeMillis() + 43200000)) // Expires in 12 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign token with secret
                .compact();                              // Return final token string
    }

    // Validate token against expected email and expiration
    public boolean validateToken(String token, UserPrincipal userPrincipal) {
        final String emailFromToken = extractUsername(token);
        return (emailFromToken.equals(userPrincipal.getEmail()) && !isTokenExpired(token));
    }

    // Extract the email (subject) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Get token expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic claim extractor method
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse token and extract claims using signing key
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}





