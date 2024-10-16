package com.example.braveCoward.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.CreateTaskRequest;
import com.example.braveCoward.dto.CreateTaskResponse;
import com.example.braveCoward.dto.TaskResponse;
import com.example.braveCoward.dto.TasksResponse;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.Task;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TaskRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Transactional(readOnly = false)
    public CreateTaskResponse createTask(Long projectId, CreateTaskRequest request) {
        User user = User.builder()
            .password("123")
            .name("준기")
            .email("gjwnsrl1012@naver.com")
            .build();

        userRepository.save(user);

        Team team = Team.builder()
            .name("준기팀")
            .description("허준기의 팀입니다.")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        teamRepository.save(team);

        TeamMember teamMember = TeamMember.builder()
            .role("백엔드")
            .position("팀장")
            .team(team)
            .user(user)
            .build();

        teamMemberRepository.save(teamMember);

        Project project = Project.builder()
            .title("테스트 프로젝트")
            .description("테스트입니다")
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now())
            .team(team)
            .build();

        projectRepository.save(project);

        /*Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));
*/
        /*TeamMember teamMember = teamMemberRepository.findById(request.teamMemberId())
            .orElseThrow(() -> new IllegalArgumentException("팀 멤버를 찾을 수 없습니다."));
*/
        Task task = Task.builder()
            .title(request.title())
            .description(request.description())
            .startDate(request.startDate())
            .endDate(request.endDate())
            .project(project)
            .teamMember(teamMember)
            .build();

        Task savedTask = taskRepository.save(task);

        return CreateTaskResponse.from(savedTask);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public TasksResponse getTasks() {
        List<TaskResponse> taskResponses = taskRepository.findAll().stream()
            .map(task -> new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStartDate(),
                task.getEndDate(),
                task.getTeamMember().getId()
            ))
            .toList();

        int totalCount = taskResponses.size();
        return new TasksResponse(totalCount, taskResponses);
    }

    public TaskResponse getTask(Long taskId) {
        Task task = taskRepository.getById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task를 찾을 수 없습니다."));

        return TaskResponse.from(task);
    }
}
