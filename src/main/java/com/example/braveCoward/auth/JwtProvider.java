package com.example.braveCoward.auth;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.braveCoward.model.User;

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

    public LocalDateTime getExpirationTime() {
        return LocalDateTime.ofInstant(Instant.now().plusMillis(expirationTime), ZoneId.systemDefault());
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("id", Long.class);
    }


    private SecretKey getSecretKey() {
        String encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }

    public static String createRefreshToken(String key) {
        Claims claims = (Claims) Jwts
                .claims();

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))//유효시간 (3일)
                .signWith(SignatureAlgorithm.HS256, key) //HS256알고리즘으로 key를 암호화 해줄것이다.
                .compact();
    }
}
