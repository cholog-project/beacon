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
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.dto.plan.CreatePlanRequest;
import com.example.braveCoward.dto.plan.CreatePlanResponse;
import com.example.braveCoward.dto.PageDTO;
import com.example.braveCoward.dto.plan.PlanResponse;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.service.PlanService;
import com.example.braveCoward.swagger.PlanApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController implements PlanApi {

    private final PlanService planService;

    @PostMapping("/{projectId}")
    public ResponseEntity<CreatePlanResponse> createPlan(
        @PathVariable Long projectId,
        @Valid @RequestBody CreatePlanRequest request
    ) {
        CreatePlanResponse response = planService.createPlan(projectId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(
        @PathVariable Long planId
    ){
        planService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponse> getPlan(
        @PathVariable Long planId
    ){
        PlanResponse response = planService.getPlan(planId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Page<PlanResponse>> getAllPlansByProject(
        @PathVariable Long projectId,
        PageDTO pageDTO
    ){
        Page<PlanResponse> response = planService.getPlansByProjectId(projectId, pageDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{planId}")
    public ResponseEntity<Void> changePlanStatus(
        @PathVariable Long planId,
        Plan.Status status
    ){
        planService.changePlanStatus(planId, status);
        return ResponseEntity.ok().build();
    }

}
