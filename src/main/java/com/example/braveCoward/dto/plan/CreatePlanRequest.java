package com.example.braveCoward.dto.plan;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

public record CreatePlanRequest(
    LocalDate startDate,
    LocalDate endDate
) {

}
