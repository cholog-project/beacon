package com.example.braveCoward.dto.project;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProjectCreateResponse(
        Long projectId,
        String title,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Double progress
) {}
