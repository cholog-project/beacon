package com.example.braveCoward.service;

import com.example.braveCoward.dto.team.MemberResponse;
import com.example.braveCoward.dto.team.TeamCreateRequest;
import com.example.braveCoward.dto.team.TeamCreateResponse;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public TeamCreateResponse createTeam(TeamCreateRequest request, String creatorEmail) {
        // 1. 프로젝트 ID 확인
        if (!projectRepository.existsById(request.projectId())) {
            throw new IllegalArgumentException("Invalid project ID: " + request.projectId());
        }

        // 2. 팀원 이메일 처리
        List<MemberResponse> members = request.memberEmails().stream()
                .map(email -> {
                    User user = userRepository.findByEmail(email)
                            .orElseGet(() -> userRepository.save(new User(email, LocalDateTime.now())));
                    return new MemberResponse(Math.toIntExact(user.getId()), user.getEmail(), user.getCreatedAt());
                })
                .toList();

        // 3. 팀 생성
        Team team = new Team(
                request.name(),
                request.description(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Team savedTeam = teamRepository.save(team);

        // 4. 팀원 정보 연결 (TeamMember 관계 생성)
        savedTeam.getTeamMembers().forEach(teamMember -> {
            // 팀원 정보가 팀에 제대로 연결되도록 처리
            teamMember.setTeam(savedTeam);
        });

        return new TeamCreateResponse(
                savedTeam.getId().longValue(),
                savedTeam.getName(),
                members,
                savedTeam.getCreatedAt(),
                savedTeam.getUpdatedAt()
        );
    }
}