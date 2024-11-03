package com.example.braveCoward.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.auth.JwtProvider;
import com.example.braveCoward.dto.MembersResponse;
import com.example.braveCoward.dto.UserLoginRequest;
import com.example.braveCoward.dto.UserLoginResponse;
import com.example.braveCoward.dto.UserRegisterRequest;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public MembersResponse getTeamMembers(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        List<TeamMember> teamMembers = project.getTeam().getTeamMembers();

        return MembersResponse.from(teamMembers);
    }

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


    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email()).get();

        if (!user.isSamePassword(passwordEncoder, request.password())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtProvider.createToken(user);

        return new UserLoginResponse(accessToken);
    }
}
