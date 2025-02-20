package com.example.braveCoward.service;

import com.example.braveCoward.dto.project.ProjectCreateRequest;
import com.example.braveCoward.dto.project.ProjectCreateResponse;
import com.example.braveCoward.dto.project.ProjectResponse;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.repository.PlanRepository;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TeamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final PlanRepository planRepository;

    public ProjectService(ProjectRepository projectRepository, TeamRepository teamRepository,
        PlanRepository planRepository) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.planRepository = planRepository;
    }

    public ProjectCreateResponse createProject(Long teamId, ProjectCreateRequest request) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));

        Project project = Project.builder()
            .title(request.title())
            .description(request.description())
            .startDate(request.startDate())
            .endDate(request.endDate())
            .progress(0.0)
            .team(team)
            .build();

        Project savedProject = projectRepository.save(project);

        return new ProjectCreateResponse(
            savedProject.getId(),
            savedProject.getTitle(),
            savedProject.getDescription(),
            savedProject.getStartDate(),
            savedProject.getEndDate(),
            savedProject.getCreatedAt(),
            savedProject.getUpdatedAt(),
            savedProject.getProgress()
        );
    }

    public ProjectResponse getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Project입니다"));
        return ProjectResponse.from(project);
    }

    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("해당projectid는 찾아지지 않습니다"));
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public void updateProjectProgress(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        long totalPlans = planRepository.countByProjectId(projectId);
        long completedPlans = planRepository.countByProjectIdAndStatus(projectId, Plan.Status.COMPLETED);

        double progress = (totalPlans > 0) ? ((double)completedPlans / totalPlans) : 0.0;
        project.updateProgress(progress);

        projectRepository.save(project);
    }

}
