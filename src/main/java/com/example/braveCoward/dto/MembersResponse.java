package com.example.braveCoward.dto;

import java.util.List;

import com.example.braveCoward.model.TeamMember;

public record MembersResponse (
    List<InnerMembersResponse> projectMembers
) {
    public record InnerMembersResponse(
        Long memberId,
        String name
    ) {

    }

    public static MembersResponse from(List<TeamMember> teamMembers) {
        List<InnerMembersResponse> members = teamMembers.stream()
            .map(member -> new InnerMembersResponse(member.getId(), member.getUser().getName()))
            .toList();
        return new MembersResponse(members);
    }
}
