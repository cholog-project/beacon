package com.example.braveCoward.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.braveCoward.dto.CreateTaskRequest;
import com.example.braveCoward.dto.CreateTaskResponse;
import com.example.braveCoward.dto.TaskResponse;
import com.example.braveCoward.dto.TasksResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/project")
@Tag(name = "(Normal) Task", description = "Task 관련 API")
public interface TaskApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Task 추가")
    @PostMapping("/tasks/{projectId}")
    ResponseEntity<CreateTaskResponse> createTask(
        @PathVariable Long projectId,
        @Valid @RequestBody CreateTaskRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Task 추가")
    @DeleteMapping("/tasks/{taskId}")
    ResponseEntity<Void> deleteTask(
        @PathVariable Long taskId
    );


    @ApiResponses(
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
    );
}
