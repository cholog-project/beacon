package com.example.braveCoward.dto.plan;

import java.time.LocalDate;

import com.example.braveCoward.model.Plan;
import com.fasterxml.jackson.annotation.JsonFormat;

public record CreatePlanRequest(
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING) Plan.Status status,
    Long teamMemberId
) {

}
