package com.example.braveCoward.dto.task;

import java.util.List;

public record TasksResponse(
    int totalCount,
    List<TaskResponse> tasks
) {

}
