package com.example.braveCoward.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.plan.CreatePlanRequest;
import com.example.braveCoward.dto.plan.CreatePlanResponse;
import com.example.braveCoward.dto.PageDTO;
import com.example.braveCoward.dto.plan.PlanResponse;
import com.example.braveCoward.global.concurrencyguard.ConcurrencyGuard;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.repository.DoRepository;
import com.example.braveCoward.repository.PlanRepository;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.util.enums.plan.PlanSearchFilter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final PlanRepository planRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final DoRepository doRepository;
    private final ProjectService projectService;

    @Transactional
    public CreatePlanResponse createPlan(Long projectId, CreatePlanRequest request) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        TeamMember assignee = teamMemberRepository.findById(request.teamMemberId())
            .orElseThrow(() -> new IllegalArgumentException("팀 멤버를 찾을 수 없습니다."));

        Plan plan = Plan.builder()
            .title(request.title())
            .description(request.description())
            .startDate(request.startDate())
            .endDate(request.endDate())
            .project(project)
            .teamMember(assignee)
            .status(request.status())
            .build();

        Plan savedPlan = planRepository.save(plan);

        projectService.updateProjectProgress(plan.getProject().getId()); // 전체 plan수 변경

        return CreatePlanResponse.from(savedPlan);
    }

    @Transactional
    public void deletePlan(Long planId) {
        Plan plan = planRepository.findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Plan입니다.: " + planId));

        doRepository.deleteByPlanId(planId);

        planRepository.delete(plan);
        projectService.updateProjectProgress(plan.getProject().getId()); // 전체 plan수 변경
    }

    public PlanResponse getPlan(Long planId) {
        Plan plan = planRepository.findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Plan입니다"));

        return PlanResponse.from(plan);
    }

    public Page<PlanResponse> getPlansByProjectId(Long projectId, PageDTO pageDTO) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

        Pageable pageable = PageRequest.of(pageDTO.page(), pageDTO.pageSize(),
            Sort.by(Sort.Direction.DESC, "id"));
        Page<Plan> plans = planRepository.findAllByProjectId(projectId, pageable);

        return plans.map(PlanResponse::from);
    }

    @Transactional
    public void changePlanStatus(Long planId, Plan.Status status) {
        Plan plan = planRepository.findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Plan입니다"));

        plan.setStatus(status);
        projectService.updateProjectProgress(plan.getProject().getId()); // project 진척도 업데이트
    }

    public Page<PlanResponse> searchPlan(String keyword, Long projectId, PlanSearchFilter filter, PageDTO pageDTO) {
        Pageable pageable = PageRequest.of(pageDTO.page() - 1, pageDTO.pageSize(),
            Sort.by(Sort.Direction.DESC, "id"));

        Page<Plan> searchedPlans = switch (filter) {
            case TITLE -> planRepository.findAllByTitleContainsAndProjectId(keyword, projectId, pageable);
            case DESCRIPTION -> planRepository.findAllByDescriptionContainsAndProjectId(keyword, projectId, pageable);
            case TITLE_AND_DESCRIPTION ->
                planRepository.findAllByTitleContainsOrDescriptionContainsAndProjectId(keyword, keyword, projectId,
                    pageable);
        };

        return searchedPlans.map(PlanResponse::from);
    }
}
