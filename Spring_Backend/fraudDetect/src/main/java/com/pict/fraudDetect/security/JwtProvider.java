package com.pict.fraudDetect.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    // Secret key for signing JWT (keep it safe in real apps)
    private static final String SECRET_KEY = "MySuperSecretKeyForHotelBooking1234567890";

    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000; // 1 day

    // Generate JWT token
    public static String generateToken(Authentication auth) {
        return Jwts.builder()
                .setIssuer("hotelbooking") // optional
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .claim("email", auth.getName())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract email from JWT token
    public String getEmailFromJwtToken(String jwt) {
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get("email", String.class);
    }

    // Validate JWT token
    public boolean validateToken(String jwt) {
        try {
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt); // if parsing succeeds, token is valid

            return true;
        }

        catch (ExpiredJwtException ex) {
            System.out.println("JWT expired: " + ex.getMessage());
        }

        catch (Exception ex) {
            System.out.println("Invalid JWT: " + ex.getMessage());
        }
        return false;
    }
}
