package com.example.braveCoward.dto.project;

import java.time.LocalDateTime;

public record ProjectCreateResponse(
        Long projectId,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Double progress
) {}
