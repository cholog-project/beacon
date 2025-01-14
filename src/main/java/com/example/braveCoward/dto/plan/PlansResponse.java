package com.example.braveCoward.dto.plan;

import java.util.List;

public record PlansResponse(
    int totalCount,
    List<PlanResponse> tasks
) {

}
