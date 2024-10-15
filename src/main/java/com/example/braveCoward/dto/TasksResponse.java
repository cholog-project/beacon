package com.example.braveCoward.dto;

import java.util.List;

public record TasksResponse(
    int totalCount,
    List<TaskResponse> tasks
) {

}
