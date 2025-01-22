package com.example.braveCoward.dto.team;

import java.time.LocalDateTime;
import java.util.List;

import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;

public record CreateTeamResponse(
    Integer teamId,
    String name,
    List<TeamMemberResponse> members,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CreateTeamResponse from(Team team, List<TeamMember> teamMembers) {
        List<TeamMemberResponse> memberResponses = teamMembers.stream()
            .map(TeamMemberResponse::from)
            .toList();

        return new CreateTeamResponse(
            team.getId(),
            team.getName(),
            memberResponses,
            team.getCreatedAt(),
            team.getUpdatedAt()
        );
    }

    public static record TeamMemberResponse(
        Long memberId,
        String email,
        LocalDateTime joinedAt
    ) {
        public static TeamMemberResponse from(TeamMember teamMember) {
            return new TeamMemberResponse(
                teamMember.getId(),
                teamMember.getUser().getEmail(),
                teamMember.getCreatedAt()
            );
        }
    }
}
