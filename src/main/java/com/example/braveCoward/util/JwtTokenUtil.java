package com.example.braveCoward.util;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.braveCoward.model.Token;
import com.example.braveCoward.repository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.secret-key}")
    private String secret;

    private final int expirationTimeMillis = 864_000_000; // 10일(밀리 초 단위)

    @Value("${jwt.refresh-token-expiration-mills}")
    private Long refreshTokenExpirationMillis;  // int → Long 변경

    private final String tokenPrefix = "Bearer ";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TokenRepository tokenRepository;

    @PostConstruct
    public void init() {
        System.out.println("JWT Secret Key: " + secret);
        System.out.println("Refresh Token Expiration: " + refreshTokenExpirationMillis);
    }

    public String createRefreshToken(Long id, Instant issuedAt) {
        if (secret == null || refreshTokenExpirationMillis == null) {
            throw new IllegalStateException("JWT 설정 값이 주입되지 않았습니다.");
        }

        String refreshToken = JWT.create()
                .withClaim("id", id)
                .withIssuedAt(issuedAt)
                .withExpiresAt(issuedAt.plusMillis(refreshTokenExpirationMillis))
                .sign(Algorithm.HMAC512(secret));

        Token token = new Token(id, refreshToken);
        tokenRepository.save(token);
        return refreshToken;
    }

    public String extractEmailFromToken(String token) {
        try {
            return JWT.decode(token).getClaim("email").asString();
        } catch (JWTDecodeException e) {
            throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.", e);
        }
    }
}
