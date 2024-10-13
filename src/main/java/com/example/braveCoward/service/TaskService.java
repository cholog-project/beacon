package com.example.braveCoward.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.CreateTaskRequest;
import com.example.braveCoward.dto.CreateTaskResponse;
import com.example.braveCoward.dto.TaskResponse;
import com.example.braveCoward.dto.TasksResponse;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.Task;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TaskRepository;
import com.example.braveCoward.repository.TeamMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;

    public CreateTaskResponse createTask(Long projectId, CreateTaskRequest request) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        TeamMember teamMember = teamMemberRepository.findById(request.teamMemberId())
            .orElseThrow(() -> new IllegalArgumentException("팀 멤버를 찾을 수 없습니다."));

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
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task를 찾을 수 없습니다."));

        return TaskResponse.from(task);
    }
}
