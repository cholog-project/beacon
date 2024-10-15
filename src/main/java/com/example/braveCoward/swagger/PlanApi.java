package com.example.braveCoward.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.braveCoward.dto.plan.CreatePlanRequest;
import com.example.braveCoward.dto.plan.CreatePlanResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/project/tasks")
@Tag(name = "(Normal) Plan", description = "Plan 관련 API")
public interface PlanApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Plan 추가")
    @PostMapping("/{taskId}/plan}")
    ResponseEntity<CreatePlanResponse> createPlan(
        @PathVariable Long taskId,
        @Valid @RequestBody CreatePlanRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Task 추가")
    @DeleteMapping("/plans/{planId}")
    ResponseEntity<Void> deletePlan(
        @PathVariable Long planId
    );


    /*@ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Task 목록 조회")
    @GetMapping("/tasks")
    ResponseEntity<TasksResponse> getTaskList();

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Task 단일 조회")
    @GetMapping("/tasks/{taskId}")
    ResponseEntity<TaskResponse> getTask(
        @PathVariable Long taskId
    );*/
}
