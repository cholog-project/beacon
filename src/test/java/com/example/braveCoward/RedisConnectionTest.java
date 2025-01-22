package com.example.braveCoward;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisConnection() {
        String key = "test-key";
        String value = "test-value";

        // 값 저장
        redisTemplate.opsForValue().set("test-key", "test-value", Duration.ofMinutes(1)); // TTL 1분


        // 저장된 값 가져오기
        String storedValue = redisTemplate.opsForValue().get(key);

        // 검증
        assertEquals(value, storedValue);
        System.out.println("Redis 연결 성공! 저장된 값: " + storedValue);
    }
}
