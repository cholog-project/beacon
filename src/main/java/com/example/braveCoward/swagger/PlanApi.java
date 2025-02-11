package com.example.braveCoward.swagger;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.braveCoward.dto.plan.CreatePlanRequest;
import com.example.braveCoward.dto.plan.CreatePlanResponse;
import com.example.braveCoward.dto.PageDTO;
import com.example.braveCoward.dto.plan.PlanResponse;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.util.enums.plan.PlanSearchFilter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/plan")
@Tag(name = "(Normal) Plan", description = "Plan 관련 API")
public interface PlanApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Plan 추가")
    @PostMapping("/{projectId}")
    ResponseEntity<CreatePlanResponse> createPlan(
        @PathVariable Long projectId,
        @Valid @RequestBody CreatePlanRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Plan 삭제")
    @DeleteMapping("/{planId}")
    ResponseEntity<Void> deletePlan(
        @PathVariable Long planId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Plan 단일 조회")
    @GetMapping("/{planId}")
    ResponseEntity<PlanResponse> getPlan(
        @PathVariable Long planId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Project내에 있는 모든 Plan 가져오기")
    @GetMapping("/project/{projectId}")
    ResponseEntity<Page<PlanResponse>> getAllPlansByProject(
        @PathVariable Long projectId,
        PageDTO pageDTO
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Plan 상태 수정")
    @PatchMapping("/{planId}")
    ResponseEntity<Void> changePlanStatus(
        @PathVariable Long planId,
        @RequestParam
        @Schema(description = "변경할 Plan 상태(NOT_STARTED, IN_PROGRESS, COMPLETED)") Plan.Status status
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Plan 검색")
    @PatchMapping("/search")
    ResponseEntity<Page<PlanResponse>> searchPlan(
        @RequestParam String keyword,
        PlanSearchFilter filter,
        PageDTO pageDTO
    );
}
