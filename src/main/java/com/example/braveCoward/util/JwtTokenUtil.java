package com.example.braveCoward.util;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.braveCoward.model.RefreshToken;
import com.example.braveCoward.repository.TokenRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.secret-key}")
    private String secret;
    @Value("${jwt.refresh-token-expiration-mills}")
    private Long refreshTokenExpirationMillis;  // int → Long 변경
    private final TokenRepository tokenRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void expire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

//    public String createRefreshToken(Long id, Instant issuedAt) {
//        String refreshToken = JWT.create()
//                .withClaim("id", id)
//                .withIssuedAt(issuedAt)
//                .withExpiresAt(issuedAt.plusMillis(refreshTokenExpirationMillis))
//                .sign(Algorithm.HMAC512(secret));
//
//        RefreshToken token = new RefreshToken(id, refreshToken);
//        tokenRepository.save(token);
//        return refreshToken;
//    }

    public String extractEmailFromToken(String token) {
        try {
            return JWT.decode(token).getClaim("email").asString();
        } catch (JWTDecodeException e) {
            throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.", e);
        }
    }
}
