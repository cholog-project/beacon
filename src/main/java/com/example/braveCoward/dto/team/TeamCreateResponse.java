package com.example.braveCoward.dto.team;

import java.time.LocalDateTime;
import java.util.List;

public record TeamCreateResponse(
    Long teamId,
    String name,
    List<MemberResponse> members,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}