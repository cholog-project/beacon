package com.example.braveCoward.swagger;

import com.example.braveCoward.dto.team.TeamCreateRequest;
import com.example.braveCoward.dto.team.TeamCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teams")
@Tag(name = "(Normal) Team", description = "Team 생성 API")
public interface TeamApi {

    @Operation(summary = "팀 생성", description = "새로운 팀을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team created successfully",
                    content = @Content(schema = @Schema(implementation = TeamCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    ResponseEntity<TeamCreateResponse> createTeam(
            @RequestBody @Parameter(description = "Team creation request", required = true) TeamCreateRequest request,
            @RequestHeader("Authorization") @Parameter(description = "JWT token for authentication", required = true) String token);
}