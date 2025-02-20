package com.example.braveCoward.util;

import com.auth0.jwt.exceptions.JWTDecodeException;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final RedisTemplate<String, Object> redisTemplate;

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

    public String extractEmailFromToken(String token) {
        try {
            return JWT.decode(token).getClaim("email").asString();
        } catch (JWTDecodeException e) {
            throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.", e);
        }
    }
}