package com.example.braveCoward.controller;

import com.example.braveCoward.dto.RefreshTokenResponse;
import com.example.braveCoward.service.RefreshTokenService;
import com.example.braveCoward.swagger.RedisApi;
import com.example.braveCoward.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class RedisController implements RedisApi {
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/refresh/{id}")
    public ResponseEntity<RefreshTokenResponse> getRefresh(@PathVariable("id") Long id) {
        String refreshToken = jwtTokenUtil.createRefreshToken(id, Instant.now());
        RefreshTokenResponse response = new RefreshTokenResponse(refreshToken);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/redis")
//    public ResponseEntity<?> saveToken(@RequestBody RefreshTokenRespone.RefreshTokenRequestDto requestDto){
//        refreshTokenService.saveTokenInfo(requestDto);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @GetMapping("/redis-token")
//    public ResponseEntity<RefreshTokenDto.RefreshTokenResponseDto> getToken(@RequestParam("token") String token){
//        RefreshTokenDto.RefreshTokenResponseDto tokenInfo = refreshTokenService.getTokenInfo(token);
//
//        return new ResponseEntity<>(tokenInfo, null, HttpStatus.OK);
//    }
//
//    @GetMapping("/redis-id")
//    public ResponseEntity<RefreshTokenDto.RefreshTokenResponseDto> getTokenById(@RequestParam("id") String id){
//        RefreshTokenDto.RefreshTokenResponseDto tokenInfoById = refreshTokenService.getTokenInfoById(id);
//
//        return new ResponseEntity<>(tokenInfoById, null, HttpStatus.OK);
//    }


    @DeleteMapping("/redis")
    public ResponseEntity<?> deleteToken(@RequestParam("token") String token){
        refreshTokenService.removeRefreshToken(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}