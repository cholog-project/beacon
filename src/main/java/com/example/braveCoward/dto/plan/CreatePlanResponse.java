package com.example.braveCoward.dto.plan;

import java.time.LocalDate;

import com.example.braveCoward.model.Plan;

public record CreatePlanResponse(
    Long id,
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate
) {
    public static CreatePlanResponse from(Plan plan) {
        return new CreatePlanResponse(
            plan.getId(),
            plan.getTitle(),
            plan.getDescription(),
            plan.getStartDate(),
            plan.getEndDate()
        );
    }
}
