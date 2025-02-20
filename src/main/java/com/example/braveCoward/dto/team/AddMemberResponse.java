package com.example.braveCoward.dto.team;

public record AddMemberResponse(
    Long memberId,
    String role,
    String position,
    Long teamId,
    Long userId
) {
}
