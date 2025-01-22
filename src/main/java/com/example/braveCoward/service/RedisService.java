package com.example.braveCoward.service;

import com.example.braveCoward.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void saveRefreshToken(Long userId, String refreshToken) {
        String key = "refresh-token:" + userId;
        redisTemplate.opsForValue().set(key, refreshToken, Duration.ofDays(7)); // TTL 설정

        // 저장된 값 검증 로직 추가
        String storedValue = redisTemplate.opsForValue().get(key);
        if (refreshToken.equals(storedValue)) {
            System.out.println("Refresh token saved successfully. Key: " + key + ", Value: " + storedValue);
        } else {
            System.err.println("Failed to save refresh token. Key: " + key);
            throw new IllegalStateException("Refresh token save operation failed for key: " + key);
        }

        //System.out.println("Saved key: " + key + ", value: " + refreshToken); // 로그 추가
    }

//    @Transactional(readOnly = true)
//    public RefreshTokenDto.RefreshTokenResponseDto getTokenInfo(String accessToken){
//        RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(accessToken)
//                .orElseThrow(() -> new RuntimeException("없다!"));
//
//        return RefreshTokenDto.RefreshTokenResponseDto.from(refreshToken);
//    }
//
//    @Transactional(readOnly = true)
//    public RefreshTokenDto.RefreshTokenResponseDto getTokenInfoById(String id){
//        RefreshToken refreshToken = refreshTokenRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("없다!"));
//
//        return RefreshTokenDto.RefreshTokenResponseDto.from(refreshToken);
//    }

//    @Transactional
//    public void removeRefreshToken(String accessToken){
//        Token token = tokenRepository.findByAccessToken(accessToken)
//                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Access Token입니다."));
//
//        tokenRepository.delete(token);
//    }

}