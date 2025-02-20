package com.example.braveCoward.dto.project;

import java.time.LocalDate;

public record ProjectCreateRequest(
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate
) {
}
