package com.example.braveCoward.dto.plan;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.braveCoward.model.Plan;

public record PlanResponse(
    Long id,
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    Plan.Status status,
    Long teamMemberId
) {

    public static PlanResponse from(Plan plan) {
        return new PlanResponse(
            plan.getId(),
            plan.getTitle(),
            plan.getDescription(),
            plan.getStartDate(),
            plan.getEndDate(),
            plan.getStatus(),
            plan.getTeamMember().getId()
        );
    }
}
