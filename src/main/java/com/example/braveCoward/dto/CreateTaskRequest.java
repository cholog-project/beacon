package com.example.braveCoward.dto;

import java.time.LocalDate;

public record CreateTaskRequest (
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    Long projectId,
    Long teamMemberId
) {}
