package com.example.braveCoward.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.team.CreateTeamRequest;
import com.example.braveCoward.dto.team.CreateTeamResponse;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public CreateTeamResponse createTeam(CreateTeamRequest request) {
        // 프로젝트 확인
        Project project = projectRepository.findById(request.projectId())
            .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + request.projectId()));

        // 팀 생성
        Team team = Team.builder()
            .name(request.name())
            .description(request.description())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Team savedTeam = teamRepository.save(team);

        // 팀원 생성
        List<TeamMember> teamMembers = request.teamMembers().stream().map(memberRequest -> {
            User user = userRepository.findByEmail(memberRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다 이메일: " + memberRequest.email()));

            return TeamMember.builder()
                .role(memberRequest.role())
                .position(memberRequest.position())
                .team(savedTeam)
                .user(user)
                .build();
        }).toList();

        teamMemberRepository.saveAll(teamMembers);

        return CreateTeamResponse.from(savedTeam, project, teamMembers);
    }
}
