package com.example.braveCoward.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RefreshTokenDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshTokenRequestDto {
        private Long id; // 사용자 ID
        private String accessToken; // 액세스 토큰
        private String refreshToken; // 리프레시 토큰
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefreshTokenResponseDto {
        private Long id; // 사용자 ID
        private String accessToken; // 액세스 토큰
        private String refreshToken; // 리프레시 토큰
        private String expirationTime; // 만료 시간

    }
}
