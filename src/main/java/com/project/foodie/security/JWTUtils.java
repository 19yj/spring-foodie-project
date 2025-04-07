package com.project.foodie.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSigner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    private String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    private long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // generate a JWT token for user with their id, expire after 10 hours
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // extract user id form token
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();  // getSubject() extracts the subject (user id)
    }

    // extract claims (expiration, subject) from token
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)  // set secret key for validation
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // check if token is valid (not expired)
    public boolean validateToken(String token) {
        Claims claims = extractClaims(token);
        return !claims.getExpiration().before(new Date());
    }

}
