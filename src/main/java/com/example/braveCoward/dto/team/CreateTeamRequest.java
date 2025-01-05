package com.example.braveCoward.dto.team;

import java.util.List;

public record CreateTeamRequest(
    String name,
    Long projectId,
    String description,
    List<TeamMemberRequest> teamMembers
) {
    public record TeamMemberRequest(
        String email,
        String role,
        String position
    ) {}
}
