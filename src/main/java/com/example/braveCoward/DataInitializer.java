package com.example.braveCoward;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ProjectRepository projectRepository;

    public DataInitializer(UserRepository userRepository, TeamRepository teamRepository,
        TeamMemberRepository teamMemberRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.projectRepository = projectRepository;
    }

    @PostConstruct
    public void init() {
        User user1 = userRepository.findByEmail("브라운@naver.com")
            .orElseGet(() -> {
                User newUser = User.builder()
                    .password("123")
                    .name("브라운")
                    .email("브라운@naver.com")
                    .build();
                return userRepository.save(newUser);
            });

        User user2 = userRepository.findByEmail("브리@naver.com")
            .orElseGet(() -> {
                User newUser = User.builder()
                    .password("123")
                    .name("브리")
                    .email("브리@naver.com")
                    .build();
                return userRepository.save(newUser);
            });
        User user3 = userRepository.findByEmail("네오@naver.com")
            .orElseGet(() -> {
                User newUser = User.builder()
                    .password("123")
                    .name("네오")
                    .email("네오@naver.com")
                    .build();
                return userRepository.save(newUser);
            });

        Team team = teamRepository.findByName("준기팀")
            .orElseGet(() -> {
                Team newTeam = Team.builder()
                    .name("준기팀")
                    .description("허준기의 팀입니다.")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
                return teamRepository.save(newTeam);
            });

        TeamMember newTeamMember = TeamMember.builder()
            .role("백엔드")
            .position("팀장")
            .team(team)
            .user(user1)
            .build();
        teamMemberRepository.save(newTeamMember);

        TeamMember newTeamMember2 = TeamMember.builder()
            .role("프론트엔드")
            .position("팀원")
            .team(team)
            .user(user2)
            .build();
        teamMemberRepository.save(newTeamMember2);

        TeamMember newTeamMember3 = TeamMember.builder()
            .role("PM")
            .position("팀원")
            .team(team)
            .user(user3)
            .build();
        teamMemberRepository.save(newTeamMember3);

        Project project = projectRepository.findById(1L)
            .orElseGet(() -> {
                Project newProject = Project.builder()
                    .title("테스트 프로젝트")
                    .description("테스트입니다")
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .team(team)
                    .build();
                return projectRepository.save(newProject);
            });
    }
}
