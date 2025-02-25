package com.example.braveCoward.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.braveCoward.dto.*;
import com.example.braveCoward.global.concurrencyguard.ConcurrencyGuard;
import com.example.braveCoward.global.extractuserid.JwtProvider;
import com.example.braveCoward.model.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    // application.yml에서 비밀 키를 주입받음
    @Value("${jwt.secret-key}")
    private String secretKey;

    public MembersResponse getTeamMembers(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: "));
        List<TeamMember> teamMembers = project.getTeam().getTeamMembers();

        return MembersResponse.from(teamMembers);
    }

    @ConcurrencyGuard(lockName = "register")
    public void userRegister(UserRegisterRequest request) {
        User user = User.builder()
            .password(passwordEncoder.encode(request.password()))
            .name(request.name())
            .email(request.email()) // 이메일 중복 검사 삭제
            .build();
        userRepository.save(user);
    }

    @Transactional
    public void userRegisterPessimistic(UserRegisterRequest request) {
        // user_lock 테이블에서 해당 이메일에 대해 비관적 락 획득
        userRepository.getLockOnUser(request.email());

        // 이제 실제 user 테이블에 INSERT
        User user = User.builder()
            .password(passwordEncoder.encode(request.password()))
            .name(request.name())
            .email(request.email())
            .build();
        userRepository.save(user);
    }

    @Transactional
    public void userRegisterNamedLock(UserRegisterRequest request) {
        userRepository.getLock(request.email()); // DB Named Lock 적용

        try {
            User user = User.builder()
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .email(request.email()) // 중복 검사 삭제
                .build();
            userRepository.save(user);
        } finally {
            userRepository.releaseLock(request.email()); // 락 해제
        }
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
