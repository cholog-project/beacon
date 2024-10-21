package com.example.braveCoward.dto.project;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectRequest {
    private Long projectId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}