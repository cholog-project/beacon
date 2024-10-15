package com.example.braveCoward.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.dto.plan.CreatePlanRequest;
import com.example.braveCoward.dto.plan.CreatePlanResponse;
import com.example.braveCoward.service.PlanService;
import com.example.braveCoward.swagger.PlanApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/tasks")
public class PlanController implements PlanApi {

    private final PlanService planService;

    @PostMapping("/{taskId}/plan")
    public ResponseEntity<CreatePlanResponse> createPlan(
        @PathVariable Long taskId,
        @Valid @RequestBody CreatePlanRequest request
    ) {
        CreatePlanResponse response = planService.createPlan(taskId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<Void> deletePlan(
        @PathVariable Long planId
    ){
        planService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
