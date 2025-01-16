package com.example.braveCoward.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@Builder
@ToString
@RedisHash(value = "token", timeToLive = 10)
////timeToLive 유효시간 4시간으로 설정
//@RedisHash(value = "jwtToken", timeToLive = 60*60*24*3)     // 설정한 값을 Redis 의 key 값 prefix 로 사용한다. ttl 은 3일
public class Token {

    @Id
    // key 값이 되며, jwtToken:{id} 위치에 auto-increment 된다.
    private Long id;

    private String refreshToken;
}