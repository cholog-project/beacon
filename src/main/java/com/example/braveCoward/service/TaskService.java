package com.example.braveCoward.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.braveCoward.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.task.CreateTaskRequest;
import com.example.braveCoward.dto.task.CreateTaskResponse;
import com.example.braveCoward.dto.task.TaskResponse;
import com.example.braveCoward.dto.task.TasksResponse;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.Task;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;

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
    private final PlanRepository planRepository;
    private final DoRepository doRepository;

    @Transactional(readOnly = false)
    public CreateTaskResponse createTask(Long projectId, CreateTaskRequest request) {

        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        TeamMember assignee = teamMemberRepository.findById(request.teamMemberId())
           .orElseThrow(() -> new IllegalArgumentException("팀 멤버를 찾을 수 없습니다."));

        Task task = Task.builder()
            .title(request.title())
            .description(request.description())
            .startDate(request.startDate())
            .endDate(request.endDate())
            .project(project)
            .teamMember(assignee)
            .build();

        Task savedTask = taskRepository.save(task);

        /*Plan plan = Plan.builder()
            .task(task)
            .startDate(task.getStartDate())
            .endDate(task.getEndDate())
            .build();
        planRepository.save(plan);*/

        return CreateTaskResponse.from(savedTask);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        // 연결된 Do 레코드 삭제
        //doRepository.deleteByTaskId(taskId);
        
        // Task 삭제
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
