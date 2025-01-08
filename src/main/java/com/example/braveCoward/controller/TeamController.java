package com.example.braveCoward.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.dto.team.CreateTeamRequest;
import com.example.braveCoward.dto.team.CreateTeamResponse;
import com.example.braveCoward.service.TeamService;
import com.example.braveCoward.swagger.TeamApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamService teamService;

    @PostMapping("/teams")
    public ResponseEntity<CreateTeamResponse> createTeams(
        @Valid @RequestBody CreateTeamRequest request
    ) {
        CreateTeamResponse response = teamService.createTeam(request);

        return ResponseEntity.ok(response);
    }
}
