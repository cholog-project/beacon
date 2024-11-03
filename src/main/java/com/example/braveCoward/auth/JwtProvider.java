package com.example.braveCoward.auth;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.braveCoward.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private final String secretKey;
    private final Long expirationTime;

    public JwtProvider(
        @Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.access-token.expiration-time}") Long expirationTime
    ) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public String createToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }
        Key key = getSecretKey();
        return Jwts.builder()
            .signWith(key)
            .header()
            .add("typ", "JWT")
            .add("alg", key.getAlgorithm())
            .and()
            .claim("id", user.getId())
            .expiration(Date.from(Instant.now().plusMillis(expirationTime)))
            .compact();
    }

    private SecretKey getSecretKey() {
        String encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }
}
