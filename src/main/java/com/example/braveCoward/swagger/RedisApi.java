package com.example.braveCoward.swagger;
import com.example.braveCoward.dto.RefreshToken.RefreshTokenRequest;
import com.example.braveCoward.dto.RefreshToken.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Redis Token", description = "Redis와 관련된 토큰 처리 API")
public interface RedisApi {
    @Operation(summary = "리프레시 토큰 발급", description = "리프레시 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/refresh")
    ResponseEntity<RefreshTokenResponse> createRefreshToken(@RequestBody RefreshTokenRequest request);

}
