package com.example.braveCoward.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginResponse (
    @Schema(
        description = "Jwt accessToken",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
        requiredMode = REQUIRED
    )
    @JsonProperty("accessToken")
    String accessToken,
    @Schema(
            description = "Jwt refreshToken",
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpZCI6NSwiZXhwIjoxNzM3Nzk1MDc1fQ.1AG6d9llG21-yLLj3G2_nQUFyMF1krkSa9oj7d5KkJH0g78oa1HvWpSqdCspVGwa",
            requiredMode = REQUIRED
    )
    @JsonProperty("refreshToken")
    String refreshToken
) {

}