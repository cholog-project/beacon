package com.example.braveCoward.controller;

import com.example.braveCoward.dto.team.TeamCreateRequest;
import com.example.braveCoward.dto.team.TeamCreateResponse;
import com.example.braveCoward.service.TeamService;
import com.example.braveCoward.swagger.TeamApi;
import com.example.braveCoward.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController implements TeamApi {

    private final TeamService teamService;
    private final JwtTokenUtil jwtTokenUtil;

    public TeamController(TeamService teamService, JwtTokenUtil jwtTokenUtil) {
        this.teamService = teamService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<TeamCreateResponse> createTeam(
            @RequestBody TeamCreateRequest request, // 요청 데이터: 팀 이름, 팀원 이메일, 프로젝트 ID
            @RequestHeader("Authorization") String token // JWT 토큰
    ) {
        // JWT 토큰에서 사용자 정보(예: 이메일) 추출
        String creatorEmail = jwtTokenUtil.extractEmailFromToken(token);

        // 서비스 호출: 요청 데이터와 생성자 이메일 전달
        TeamCreateResponse response = teamService.createTeam(request, creatorEmail);

        return ResponseEntity.status(201).body(response);
    }
}