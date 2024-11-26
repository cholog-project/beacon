package com.example.braveCoward.dto.project;

import java.time.LocalDateTime;

public record ProjectCreateRequest(
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}