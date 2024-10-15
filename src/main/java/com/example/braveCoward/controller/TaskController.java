package com.example.braveCoward.controller;

import com.example.braveCoward.dto.*;
import com.example.braveCoward.service.DoService;
import com.example.braveCoward.swagger.TaskApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class TaskController implements TaskApi {

    private final TaskService taskService;
    private final DoService doService;

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<CreateTaskResponse> createTask(
        @PathVariable Long projectId,
        @Valid @RequestBody CreateTaskRequest request
    ) {
        CreateTaskResponse response = taskService.createTask(projectId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
        @PathVariable Long taskId
    ) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks")
    public ResponseEntity<TasksResponse> getTaskList() {
        TasksResponse response = taskService.getTasks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long taskId) {
        TaskResponse response = taskService.getTask(taskId);
        return ResponseEntity.ok(response);
    }

}
