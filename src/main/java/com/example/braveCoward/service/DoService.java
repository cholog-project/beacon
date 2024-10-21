package com.example.braveCoward.service;

import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;
import com.example.braveCoward.model.*;
import com.example.braveCoward.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final DoRepository doRepository;

    @Transactional(readOnly = false)
    public CreateDoResponse createDo(Long taskId, CreateDoRequest request) {

        User user = userRepository.findByEmail("gjwnsrl1012@naver.com")
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .password("123")
                            .name("준기")
                            .email("gjwnsrl1012@naver.com")
                            .build();
                    return userRepository.save(newUser);
                });

        User user2 = userRepository.findByEmail("gjwnsrl1012@gmail.com")
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .password("1234")
                            .name("준기2")
                            .email("gjwnsrl1012@gmail.com")
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

        TeamMember teamMember = teamMemberRepository.findByTeamAndUser(team, user)
                .orElseGet(() -> {
                    TeamMember newTeamMember = TeamMember.builder()
                            .role("백엔드")
                            .position("팀장")
                            .team(team)
                            .user(user)
                            .build();
                    return teamMemberRepository.save(newTeamMember);
                });

        TeamMember teamMember2 = teamMemberRepository.findByTeamAndUser(team, user2)
                .orElseGet(() -> {
                    TeamMember newTeamMember = TeamMember.builder()
                            .role("프론트엔드")
                            .position("팀원")
                            .team(team)
                            .user(user2)
                            .build();
                    return teamMemberRepository.save(newTeamMember);
                });

        // 일단 예시로 5L 들어가게
        Long projectId = 5L;
        Project project = projectRepository.findById(projectId)
                .orElseGet(() -> {
                    Project newProject = Project.builder()
                            .title("테스트 프로젝트")
                            .description("테스트입니다")
                            .startDate(LocalDateTime.now())
                            .endDate(LocalDateTime.now())
                            .team(team)
                            .build();
                    return projectRepository.save(newProject);
                });

        Task task = taskRepository.findById(taskId)
                .orElseGet(() -> {
                    Task newTask = Task.builder()
                            .title("테스트 테스크")
                            .description("테스트입니다")
                            .startDate(LocalDate.from(LocalDateTime.now()))
                            .endDate(LocalDate.from(LocalDateTime.now()))
                            .project(project)
                            .teamMember(teamMember)
                            .build();
                   return taskRepository.save(newTask);
                });

        Do doEntity = Do.builder()
                .date(request.date())
                .status(request.status())
                .description(request.description())
                .build();

        Do savedDo = doRepository.save(doEntity);

        return CreateDoResponse.from(savedDo);
    }

    public void deleteDo(Long doId) {
        doRepository.deleteById(doId);
    }

    public DosResponse getDos(Long taskId) {
        List<DoResponse> doResponses = doRepository.findAll().stream()
                .map(doEntity -> new DoResponse(
                        doEntity.getId(),
                        doEntity.getDate(),
                        doEntity.getStatus(),
                        doEntity.getDescription(),
                        taskId
                ))
                .toList();

        int totalCount = doResponses.size();
        return new DosResponse(totalCount, doResponses);
    }

    public DoResponse getDo(Long doId) {
        Do doEntity = doRepository.findById(doId)
                .orElseThrow(() -> new IllegalArgumentException("Do를 찾을 수 없습니다."));

        return DoResponse.from(doEntity);
    }
}
