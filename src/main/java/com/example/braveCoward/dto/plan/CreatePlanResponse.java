package com.example.braveCoward.dto.plan;

import java.time.LocalDate;

import com.example.braveCoward.model.Plan;

public record CreatePlanResponse(
    Long id,
    LocalDate startDate,
    LocalDate endDate,
    Long taskId
) {
    public static CreatePlanResponse from(Plan plan) {
        return new CreatePlanResponse(
            plan.getId(),
            plan.getStartDate(),
            plan.getEndDate(),
            plan.getTask().getId()
        );
    }
}
