package com.example.braveCoward.controller;

import com.example.braveCoward.dto.Do.ChangeDoRequest;
import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;
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
@RequestMapping("/dos")
public class DoController implements DoApi {

    private final DoService doService;

    @PostMapping("/new/{planId}")
    public ResponseEntity<CreateDoResponse> createDo(
        @PathVariable Long planId,
        @Valid @RequestBody CreateDoRequest request
    ) {
        CreateDoResponse response = doService.createDo(planId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{doId}")
    public ResponseEntity<Void> deleteDo(
        @PathVariable Long doId
    ) {
        doService.deleteDo(doId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/plan/{planId}/")
    public ResponseEntity<DosResponse> getDoList(
        @PathVariable Long planId
    ) {
        DosResponse response = doService.getDos(planId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{doId}")
    public ResponseEntity<DoResponse> getDo(
        @PathVariable Long doId
    ) {
        DoResponse response = doService.getDo(doId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dos")
    public ResponseEntity<DosResponse> getAllDo() {
        DosResponse response = doService.getAllDo();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{doId}")
    public ResponseEntity<Void> changeDo(
        @PathVariable Long doId,
        ChangeDoRequest request
    ) {
        doService.changeDo(doId, request);

        return ResponseEntity.ok().build();
    }
}
