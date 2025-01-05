package com.example.braveCoward.swagger;

import com.example.braveCoward.dto.project.ProjectCreateRequest;
import com.example.braveCoward.dto.project.ProjectCreateResponse;
import com.example.braveCoward.global.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "(Normal) Project", description = "Project 생성 API")
public interface ProjectApi {

    @Operation(summary = "프로젝트 생성", description = "새로운 프로젝트를 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "프로젝트 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없음")
    })
    @SecurityRequirement(name = "Jwt Authentication")
    @PostMapping("/teams/{teamId}/projects")
    ResponseEntity<ProjectCreateResponse> createProject(
        @PathVariable Long teamId,
        @RequestBody ProjectCreateRequest request,
        @UserId Integer userId
    );
}
