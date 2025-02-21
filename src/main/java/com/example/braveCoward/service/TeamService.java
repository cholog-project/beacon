package com.example.braveCoward.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.braveCoward.exception.CustomException;
import com.example.braveCoward.exception.ErrorStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.team.CreateTeamRequest;
import com.example.braveCoward.dto.team.CreateTeamResponse;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public CreateTeamResponse createTeam(CreateTeamRequest request) {

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

        return CreateTeamResponse.from(savedTeam, teamMembers);
    }

    @Transactional
    // 팀 삭제
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new CustomException(ErrorStatus.TEAM_NOT_FOUND));

        // 팀원 먼저 삭제
        teamMemberRepository.deleteByTeamId(teamId);

        // 팀 삭제
        teamRepository.delete(team);
    }

}
