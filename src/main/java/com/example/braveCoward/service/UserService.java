package com.example.braveCoward.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.braveCoward.dto.*;
import com.example.braveCoward.model.*;
import com.example.braveCoward.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.auth.JwtProvider;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.UserRepository;
import com.example.braveCoward.repository.UserTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final TokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    // application.yml에서 비밀 키를 주입받음
    @Value("${jwt.secret-key}")
    private String secretKey;

    public MembersResponse getTeamMembers(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        List<TeamMember> teamMembers = project.getTeam().getTeamMembers();

        return MembersResponse.from(teamMembers);
    }

    @Transactional
    public void userRegister(UserRegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
            .password(passwordEncoder.encode(request.password()))
            .name(request.name())
            .email(request.email())
            .build();
        userRepository.save(user);
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 잘못되었습니다."));

        if (!user.isSamePassword(passwordEncoder, request.password())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        // 로그인 성공 시 토큰 생성
        String accessToken = jwtProvider.createToken(user);
        // createRefreshToken에서 레디스에 리프레쉬 토큰 저장
        String refreshToken = jwtProvider.createRefreshToken(user);
        LocalDateTime expirationTime = jwtProvider.getExpirationTime();

//        //레디스에 저장 Refresh 토큰을 저장한다. (사용자 기본키 Id, refresh 토큰)
//        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));

        userTokenRepository.save(UserToken.builder()
            .user(user)
            .accessToken(accessToken)
            .expirationTime(expirationTime)
            .build()
        );

        return new UserLoginResponse(accessToken, refreshToken);
    }
}