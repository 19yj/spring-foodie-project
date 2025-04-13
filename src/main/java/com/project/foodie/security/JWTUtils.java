package com.project.foodie.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtils {
    // generate secret key and encode into base64 string
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA512");
    }

    // generate a JWT token for user with their id, expire after 10 hours
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // extract user id form token
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();  // getSubject() extracts the subject (user id)
    }

    // extract claims (expiration, subject) from token
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())  // set secret key for validation
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
