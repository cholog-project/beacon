package com.example.braveCoward.dto;

import java.time.LocalDate;

import com.example.braveCoward.model.Task;

public record TaskResponse(
    Long id,
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    Integer teamMemberId
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStartDate(),
            task.getEndDate(),
            task.getTeamMember().getId()
        );
    }
}
