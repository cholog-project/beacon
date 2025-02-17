package com.example.braveCoward.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.dto.Do.ChangeDoRequest;
import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;
import com.example.braveCoward.dto.PageDTO;
import com.example.braveCoward.service.DoService;
import com.example.braveCoward.swagger.DoApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    @GetMapping("/plan/{planId}")
    public ResponseEntity<DosResponse> getDoList(
        @PathVariable Long planId,
        PageDTO pageDTO
    ) {
        DosResponse response = doService.getDos(planId, pageDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{doId}")
    public ResponseEntity<DoResponse> getDo(
        @PathVariable Long doId
    ) {
        DoResponse response = doService.getDo(doId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
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

    @GetMapping("/search")
    public ResponseEntity<Page<DoResponse>> searchDo(
        @RequestParam String keyword,
        @Valid PageDTO pageDTO
    ) {
        Page<DoResponse> responses = doService.searchDo(keyword, pageDTO);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/complete/{doId}")
    public ResponseEntity<Void> completeDo(
        @PathVariable Long doId
    ){
        doService.completeDo(doId);
        return ResponseEntity.ok().build();
    }
}
