package com.example.braveCoward.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenResponse {
    private String refreshToken;
}