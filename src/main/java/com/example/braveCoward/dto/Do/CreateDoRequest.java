package com.example.braveCoward.dto.Do;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record CreateDoRequest(
    LocalDate startDate,
    String description
) {
}
