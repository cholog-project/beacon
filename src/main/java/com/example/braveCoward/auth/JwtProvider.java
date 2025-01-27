package com.example.braveCoward.auth;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import com.example.braveCoward.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.braveCoward.model.User;

@Component
public class JwtProvider {

    private final String secretKey;
    private final Long accessExpiration;
    private final Long refreshExpiration;
    private final JwtTokenUtil redisUtil;

    public JwtProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-token-expiration-time}") Long accessExpiration,
            @Value("${jwt.refresh-token-expiration-mills}") Long refreshExpiration,
            JwtTokenUtil redisUtil
    ) {
        this.secretKey = secretKey;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.redisUtil = redisUtil;
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
                .expiration(Date.from(Instant.now().plusMillis(accessExpiration)))
                .compact();
    }

    public LocalDateTime getExpirationTime() {
        return LocalDateTime.ofInstant(Instant.now().plusMillis(accessExpiration), ZoneId.systemDefault());
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

    public String createRefreshToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }

        Key key = getSecretKey();

        String refreshToken = Jwts.builder()
                .signWith(key)
                .header()
                .add("typ", "JWT")
                .add("alg", key.getAlgorithm())
                .and()
                .claim("id", user.getId())
                .expiration(Date.from(Instant.now().plusMillis(refreshExpiration)))
                .compact();

        redisUtil.set(user.getEmail(), refreshToken);
        redisUtil.expire(user.getEmail(), refreshExpiration, TimeUnit.MILLISECONDS);

        // Redis에 저장된 값 확인을 위한 로그 출력
        String storedToken = (String) redisUtil.get(user.getEmail());
        if (storedToken != null) {
            System.out.println("Redis에 저장된 refreshToken: " + storedToken);
        } else {
            System.out.println("Redis에 refreshToken이 저장되지 않았습니다.");
        }

        return refreshToken;
    }
}
