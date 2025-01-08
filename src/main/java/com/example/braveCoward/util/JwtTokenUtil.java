package com.example.braveCoward.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {


    // application.yml에서 비밀 키를 주입받음
    @Value("${jwt.secret-key}")
    private String secretKey;

    // JWT 토큰에서 이메일 정보 추출
    public String extractEmailFromToken(String token) {
        try {
            Module claims = Jwts.parser()
                    .setSigningKey(secretKey) // application.yml에서 주입받은 비밀 키 사용
                    .getClass() // 토큰 파싱
                    .getModule(); // 클레임 추출

            return claims.getName(); // JWT의 "subject"에 저장된 이메일 주소 반환
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }
}