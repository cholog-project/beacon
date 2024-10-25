package com.example.braveCoward.controller;

import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;
import com.example.braveCoward.model.Do;
import com.example.braveCoward.service.DoService;
import com.example.braveCoward.swagger.DoApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j  // Lombok을 사용한 로깅
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

    @GetMapping("/{taskId}/dos")
    public ResponseEntity<DosResponse> getDoList(@PathVariable Long taskId) {
        DosResponse response = doService.getDos(taskId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dos/{doId}")
    public ResponseEntity<DoResponse> getDo(@PathVariable Long doId) {
        DoResponse response = doService.getDo(doId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dos")
    public ResponseEntity<DosResponse> getAllDo(){
        DosResponse response = doService.getAllDo();

        return ResponseEntity.ok(response);
    }
}
