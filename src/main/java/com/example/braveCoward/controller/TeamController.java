package com.example.braveCoward.controller;

import com.example.braveCoward.auth.JwtProvider;
import com.example.braveCoward.dto.team.TeamCreateRequest;
import com.example.braveCoward.dto.team.TeamCreateResponse;
import com.example.braveCoward.service.TeamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final JwtProvider jwtProvider;

    public TeamController(TeamService teamService, JwtProvider jwtProvider) {
        this.teamService = teamService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    public ResponseEntity<TeamCreateResponse> createTeam(
            @RequestBody TeamCreateRequest request,
            @RequestHeader("Authorization") String token) {
        // JWT 토큰에서 사용자 ID를 추출
        Long userId = jwtProvider.getUserIdFromToken(token);

        // 팀 생성 로직 실행
        TeamCreateResponse response = teamService.createTeam(request, userId);
        return ResponseEntity.status(201).body(response);
    }
}
