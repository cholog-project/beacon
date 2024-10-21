package com.example.braveCoward.dto.Do;

import com.example.braveCoward.model.Do;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record CreateDoRequest(
    LocalDate date,
    @JsonFormat(shape = JsonFormat.Shape.STRING)Do.Status status,
    String description,
    Long taskId
) {
}
