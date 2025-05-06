package com.project.foodie.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTUtils {
    private final String base64Key;

    @Autowired
    public JWTUtils(@Value("${jwt.secret}")String base64Key) {
        this.base64Key = base64Key;
    }

    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        System.out.println("Decoded Key Length: " + decodedKey.length);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    // generate a JWT token for user with their username, expire after 10 hours
    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60);  // 1 hour

        String normalizedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", normalizedRole)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // extract claims (expiration, subject) from token
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = extractClaims(token);
        Object rolesClaim = claims.get("roles");

        // Handle both String and List<String> roles
        if (rolesClaim instanceof String) {
            return Collections.singletonList((String) rolesClaim);
        } else if (rolesClaim instanceof List) {
            return (List<String>) rolesClaim;
        }
        return Collections.emptyList();
    }

    // check if token is valid (not expired)
    public boolean validateToken(String token) {
        Claims claims = extractClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // helper method to extract username from request
    public String extractUsernameFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null && !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);  // remove "Bearer"
        return extractUsername(token);
    }

}














