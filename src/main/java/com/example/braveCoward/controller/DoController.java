package com.example.braveCoward.controller;

import com.example.braveCoward.dto.CreateDoRequest;
import com.example.braveCoward.dto.CreateDoResponse;
import com.example.braveCoward.service.DoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/tasks")
public class DoController {

    private final DoService doService;

    @PostMapping("/{taskId}/dos")
    public ResponseEntity<CreateDoResponse> createDo(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateDoRequest request
    ) {
        CreateDoResponse response = doService.createDo(taskId, request);
        return ResponseEntity.ok(response);
    }
}
