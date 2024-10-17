package com.example.braveCoward.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.braveCoward.dto.MembersResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "(Normal) User", description = "User 관련 API")
public interface UserApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
        }
    )
    @Operation(summary = "프로젝트 팀 멤버 조회")
    @GetMapping("/projects/{projectId}/members")
    ResponseEntity<MembersResponse> getMembers(
        @PathVariable Long projectId
    );
}
