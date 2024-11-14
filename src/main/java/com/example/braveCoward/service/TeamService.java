package com.example.braveCoward.service;

import com.example.braveCoward.dto.team.TeamCreateRequest;
import com.example.braveCoward.dto.team.TeamCreateResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService {

    public TeamCreateResponse createTeam(TeamCreateRequest request, Long userId) {

        return new TeamCreateResponse(
                1L,
                request.name(),
                request.projectId(),
                List.of(
                        new TeamCreateResponse.MemberResponse(101L, "member1@example.com", LocalDateTime.now()),
                        new TeamCreateResponse.MemberResponse(102L, "member2@example.com", LocalDateTime.now()),
                        new TeamCreateResponse.MemberResponse(103L, "member3@example.com", LocalDateTime.now())
                ),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
