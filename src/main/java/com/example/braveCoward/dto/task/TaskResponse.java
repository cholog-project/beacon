package com.example.braveCoward.dto.task;

import java.time.LocalDate;

import com.example.braveCoward.model.Task;

public record TaskResponse(
    Long id,
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate,
    Long teamMemberId
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
