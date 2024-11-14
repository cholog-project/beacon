package com.example.braveCoward.dto.team;

import java.time.LocalDateTime;
import java.util.List;

public record TeamCreateResponse(
        Long teamId,
        String name,
        Long projectId,
        List<MemberResponse> members,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record MemberResponse(
            Long memberId,
            String email,
            LocalDateTime joinedAt
    ) {}
}