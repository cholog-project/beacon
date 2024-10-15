package com.example.braveCoward.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.plan.CreatePlanRequest;
import com.example.braveCoward.dto.plan.CreatePlanResponse;
import com.example.braveCoward.dto.plan.PlanResponse;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.Task;
import com.example.braveCoward.repository.PlanRepository;
import com.example.braveCoward.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final TaskRepository taskRepository;
    private final PlanRepository planRepository;

    public CreatePlanResponse createPlan(Long taskId, CreatePlanRequest request) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("태스크를 찾을 수 없습니다."));

        Plan plan = Plan.builder()
            .startDate(request.startDate())
            .endDate(request.endDate())
            .task(task)
            .build();

        Plan savedPlan = planRepository.save(plan);

        return CreatePlanResponse.from(savedPlan);
    }

    public void deletePlan(Long planId) {
        planRepository.deleteById(planId);
    }

    public PlanResponse getPlan(Long planId){
        Plan plan = planRepository.findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Plan입니다"));

        return PlanResponse.from(plan);
    }
}
