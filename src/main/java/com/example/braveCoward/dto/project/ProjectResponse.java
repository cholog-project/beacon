package com.example.braveCoward.dto.project;

import com.example.braveCoward.model.Project;

import java.time.LocalDateTime;

public record ProjectResponse(
        Long id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Double progress,
        String teamName

) {


    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getProgress(),
                project.getTeam().getName()
        );
    }
}