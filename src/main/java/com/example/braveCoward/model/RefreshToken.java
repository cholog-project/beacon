package com.example.braveCoward.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refresh_token", timeToLive = 10)
@AllArgsConstructor
@ToString
@Getter
public class RefreshToken {
    @Id
    private Long id;
    private String refreshToken;
}