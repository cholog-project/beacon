package com.example.braveCoward.controller;

import com.example.braveCoward.dto.project.ProjectCreateRequest;
import com.example.braveCoward.dto.project.ProjectCreateResponse;
import com.example.braveCoward.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{teamId}")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectCreateResponse> createProject(
            @PathVariable Long teamId,
            @RequestBody ProjectCreateRequest request) {
        ProjectCreateResponse response = projectService.createProject(teamId, request);
        return ResponseEntity.status(201).body(response);
    }
}
