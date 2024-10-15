package com.example.braveCoward.controller;

import com.example.braveCoward.dto.CreateDoRequest;
import com.example.braveCoward.dto.CreateDoResponse;
import com.example.braveCoward.dto.TasksResponse;
import com.example.braveCoward.service.DoService;
import com.example.braveCoward.swagger.DoApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/tasks")
public class DoController implements DoApi {

    private final DoService doService;

    @PostMapping("/{taskId}/dos")
    public ResponseEntity<CreateDoResponse> createDo(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateDoRequest request
    ) {
        CreateDoResponse response = doService.createDo(taskId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/dos/{doId}")
    public ResponseEntity<Void> deleteDo(
            @PathVariable Long doId
    ) {
        doService.deleteDo(doId);
        return ResponseEntity.noContent().build();
    }

}
