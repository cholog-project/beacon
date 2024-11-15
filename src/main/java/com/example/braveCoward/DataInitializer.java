package com.example.braveCoward;

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

    public DataInitializer(UserRepository userRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.projectRepository = projectRepository;
    }

    @PostConstruct
    public void init() {
        User junki = userRepository.findByEmail("gjwnsrl@naver.com")
            .orElseGet(() -> {
                User newUser = User.builder()
                    .password("123")
                    .name("허준기")
                    .email("gjwnsrl1012@naver.com")
                    .build();
                return userRepository.save(newUser);
            });

        User seongjae = userRepository.findByEmail("rlatjdwo@naver.com")
            .orElseGet(() -> {
                User newUser = User.builder()
                    .password("123")
                    .name("김성재")
                    .email("rlatjdwo@naver.com")
                    .build();
                return userRepository.save(newUser);
            });
        User doyeon = userRepository.findByEmail("rlaehdus@naver.com")
            .orElseGet(() -> {
                User newUser = User.builder()
                    .password("123")
                    .name("김도연")
                    .email("rlaehdus@naver.com")
                    .build();
                return userRepository.save(newUser);
            });

        User suhyeon = userRepository.findByEmail("rlaehdus@naver.com")
            .orElseGet(() -> {
                User newUser = User.builder()
                    .password("123")
                    .name("김도연")
                    .email("rlaehdus@naver.com")
                    .build();
                return userRepository.save(newUser);
            });

        Team team = teamRepository.findByName("용감한 겁쟁이")
            .orElseGet(() -> {
                Team newTeam = Team.builder()
                    .name("옹감한 겁쟁이")
                    .description("용감한 겁쟁이 팀입니다.")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
                return teamRepository.save(newTeam);
            });

        TeamMember teamMember1 = teamMemberRepository.findByTeamAndUser(team, junki)
            .orElseGet(() -> {
                TeamMember newTeamMember = TeamMember.builder()
                    .role("백엔드")
                    .position("팀장")
                    .team(team)
                    .user(junki)
                    .build();
                return teamMemberRepository.save(newTeamMember);
            });

        TeamMember teamMember2 = teamMemberRepository.findByTeamAndUser(team, seongjae)
            .orElseGet(() -> {
                TeamMember newTeamMember2 = TeamMember.builder()
                    .role("프론트엔드")
                    .position("팀원")
                    .team(team)
                    .user(seongjae)
                    .build();
                return teamMemberRepository.save(newTeamMember2);
            });

        TeamMember teamMember3 = teamMemberRepository.findByTeamAndUser(team, doyeon)
            .orElseGet(() -> {
                TeamMember newTeamMember3 = TeamMember.builder()
                    .role("PM")
                    .position("팀원")
                    .team(team)
                    .user(doyeon)
                    .build();
                return teamMemberRepository.save(newTeamMember3);
            });

        TeamMember teamMember4 = teamMemberRepository.findByTeamAndUser(team, suhyeon)
            .orElseGet(() -> {
                TeamMember newTeamMember4 = TeamMember.builder()
                    .role("PM")
                    .position("팀원")
                    .team(team)
                    .user(suhyeon)
                    .build();
                return teamMemberRepository.save(newTeamMember4);
            });

        Project project = projectRepository.findById(1L)
            .orElseGet(() -> {
                Project newProject = Project.builder()
                    .title("일정 관리 프로젝트")
                    .description("용감한 겁쟁이 팀의 일정관리 프로젝트입니다.")
                    .startDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now())
                    .team(team)
                    .build();
                return projectRepository.save(newProject);
            });
    }
}
