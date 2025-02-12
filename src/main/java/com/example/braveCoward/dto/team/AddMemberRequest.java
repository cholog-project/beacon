package com.example.braveCoward.dto.team;

public record AddMemberRequest(
        Long teamId,
        Long userId,
        String role,
        String position
) {
}
