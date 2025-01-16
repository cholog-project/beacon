package com.example.braveCoward.service;

import com.example.braveCoward.model.Token;
import com.example.braveCoward.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final TokenRepository tokenRepository;

//    @Transactional
//    public void saveTokenInfo(RefreshTokenDto.RefreshTokenRequestDto requestDto){
//        tokenRepository.save(new Token(requestDto.getId(), requestDto.getRefreshToken()));
//    }

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

    @Transactional
    public void removeRefreshToken(String accessToken){
        Token token = tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Access Token입니다."));

        tokenRepository.delete(token);
    }

}