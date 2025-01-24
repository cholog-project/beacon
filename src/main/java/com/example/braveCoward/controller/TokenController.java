package com.example.braveCoward.controller;

import com.example.braveCoward.dto.RefreshToken.RefreshTokenRequest;
import com.example.braveCoward.dto.RefreshToken.RefreshTokenResponse;
import com.example.braveCoward.service.RedisService;
import com.example.braveCoward.swagger.RedisApi;
import com.example.braveCoward.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

//@RestController
//@RequiredArgsConstructor
//public class TokenController implements RedisApi {
//    private final RedisService redisService;
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @PostMapping("/refresh")
//    public ResponseEntity<RefreshTokenResponse> createRefreshToken(@RequestBody RefreshTokenRequest request) {
//        Long userId = request.userId(); // 클라이언트에서 전달된 사용자 ID
//        String refreshToken = jwtTokenUtil.createRefreshToken(userId, Instant.now());
//
//        // Redis에 저장
//        redisService.saveRefreshToken(userId, refreshToken);
//
//        return ResponseEntity.ok(new RefreshTokenResponse(refreshToken));
//    }
//
//}