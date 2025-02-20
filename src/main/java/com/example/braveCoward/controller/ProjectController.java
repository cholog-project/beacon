package com.example.braveCoward.controller;

import com.example.braveCoward.dto.project.ProjectCreateRequest;
import com.example.braveCoward.dto.project.ProjectCreateResponse;

import com.example.braveCoward.global.UserId;
import com.example.braveCoward.dto.project.ProjectResponse;

import com.example.braveCoward.service.ProjectService;
import com.example.braveCoward.swagger.ProjectApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController implements ProjectApi {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/{teamId}")
    public ResponseEntity<ProjectCreateResponse> createProject(
        @PathVariable Long teamId,
        @RequestBody ProjectCreateRequest request,
        @UserId Integer userId
    ) {
        ProjectCreateResponse response = projectService.createProject(teamId, request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long projectId) {
        ProjectResponse response = projectService.getProject(projectId);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
        @PathVariable Long projectId
    ) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

}
