package com.example.braveCoward.controller;

import com.example.braveCoward.dto.team.AddMemberRequest;
import com.example.braveCoward.dto.team.AddMemberResponse;
import com.example.braveCoward.service.TeamMemberService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.braveCoward.dto.team.CreateTeamRequest;
import com.example.braveCoward.dto.team.CreateTeamResponse;
import com.example.braveCoward.service.TeamService;
import com.example.braveCoward.swagger.TeamApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController implements TeamApi {

    private final TeamService teamService;
    private final TeamMemberService teamMemberService;

    @PostMapping("")
    public ResponseEntity<CreateTeamResponse> createTeams(
        @Valid @RequestBody CreateTeamRequest request
    ) {
        CreateTeamResponse response = teamService.createTeam(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(
        @PathVariable Long teamId
    ) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }

    @PostMapping("/{teamId}/members")
    public ResponseEntity<AddMemberResponse> addMemberToTeam(
        @Valid @RequestBody AddMemberRequest request
    ) {
        AddMemberResponse response = teamMemberService.addMember(request);
        return ResponseEntity.ok(response);
    }

}
