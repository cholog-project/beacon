package com.example.braveCoward.dto.plan;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.braveCoward.model.Plan;

public record PlanResponse(
    Long id,
    LocalDate startDate,
    LocalDate endDate,
    Long taskId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

    public static PlanResponse from(Plan plan) {
        return new PlanResponse(
            plan.getId(),
            plan.getStartDate(),
            plan.getEndDate(),
            plan.getTask().getId(),
            plan.getCreatedAt(),
            plan.getUpdatedAt()
        );
    }

}
