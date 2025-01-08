package com.example.braveCoward.swagger;
import com.example.braveCoward.dto.RefreshTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/redis")
@Tag(name = "Redis Token", description = "Redis와 관련된 토큰 처리 API")
public interface RedisApi {

    @Operation(summary = "토큰 저장", description = "Redis에 토큰 정보를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    ResponseEntity<?> saveToken(@RequestBody RefreshTokenDto.RefreshTokenRequestDto requestDto);

//    @Operation(summary = "토큰 조회", description = "Redis에서 토큰 정보를 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "토큰 정보 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "토큰을 찾을 수 없음")
//    })
//    @GetMapping("/redis-token")
//    ResponseEntity<RefreshTokenDto.RefreshTokenResponseDto> getToken(@RequestParam("token") String token);
//
//    @Operation(summary = "ID로 토큰 조회", description = "ID로 Redis에서 토큰 정보를 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "ID로 토큰 정보 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "ID로 토큰을 찾을 수 없음")
//    })
//    @GetMapping("/redis-id")
//    ResponseEntity<RefreshTokenDto.RefreshTokenResponseDto> getTokenById(@RequestParam("id") String id);

    @Operation(summary = "토큰 삭제", description = "Redis에서 토큰을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "토큰을 찾을 수 없음")
    })
    @DeleteMapping
    ResponseEntity<?> deleteToken(@RequestParam("token") String token);
}
