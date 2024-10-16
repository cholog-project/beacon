package com.example.braveCoward.dto.task;

import java.time.LocalDate;

import com.example.braveCoward.model.Task;

public record CreateTaskResponse(
    Long taskId,
    String title,
    String description,
    LocalDate startDate,
    LocalDate endDate
) {
    public static CreateTaskResponse from(Task task) {
        return new CreateTaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStartDate(),
            task.getEndDate()
        );
    }
}
