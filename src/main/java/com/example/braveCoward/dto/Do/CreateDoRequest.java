package com.example.braveCoward.dto.Do;

import com.example.braveCoward.model.Do;

import java.time.LocalDate;

public record CreateDoRequest(
    LocalDate date,
    Do.Status status,
    String description,
    Long taskId
) {
}
