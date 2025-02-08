package com.example.braveCoward.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.Do.ChangeDoRequest;
import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;
import com.example.braveCoward.dto.PageDTO;
import com.example.braveCoward.dto.plan.PlanResponse;
import com.example.braveCoward.model.Do;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.repository.DoRepository;
import com.example.braveCoward.repository.PlanRepository;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TaskRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;
import com.example.braveCoward.util.enums.plan.PlanSearchFilter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final DoRepository doRepository;
    private final PlanRepository planRepository;

    @Transactional(readOnly = false)
    public CreateDoResponse createDo(Long planId, CreateDoRequest request) {

        Plan plan = planRepository.findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("Plan을 찾을 수 없습니다."));

        System.out.println(request.startDate() + "날짜 로깅");
        Do doEntity = Do.builder()
            .date(request.startDate())
            .description(request.description())
            .plan(plan)
            .build();

        Do savedDo = doRepository.save(doEntity);

        return CreateDoResponse.from(savedDo);
    }

    @Transactional
    public void deleteDo(Long doId) {
        doRepository.deleteById(doId);
    }

    public DosResponse getDos(Long planId) {
        List<DoResponse> doResponses = doRepository.findAllByPlanId(planId).stream()
            .map(doEntity -> new DoResponse(
                doEntity.getId(),
                doEntity.getDate(),
                doEntity.getDescription(),
                planId
            ))
            .toList();

        int totalCount = doResponses.size();
        return new DosResponse(totalCount, doResponses);
    }

    public DoResponse getDo(Long doId) {
        Do doEntity = doRepository.findById(doId)
            .orElseThrow(() -> new IllegalArgumentException("Do를 찾을 수 없습니다."));

        return DoResponse.from(doEntity);
    }

    public DosResponse getAllDo() {
        List<DoResponse> doResponses = doRepository.findAll().stream()
            .map(doEntity -> new DoResponse(
                doEntity.getId(),
                doEntity.getDate(),
                doEntity.getDescription(),
                doEntity.getPlan().getId()
            ))
            .toList();

        int totalCount = doResponses.size();
        return new DosResponse(totalCount, doResponses);
    }

    @Transactional(readOnly = false)
    public void changeDo(Long doId, ChangeDoRequest request) {
        Do doEntity = doRepository.findById(doId)
            .orElseThrow(() -> new IllegalArgumentException("Do를 찾을 수 없습니다."));

        doEntity.setDescription(request.description());
        doEntity.setDate(request.date());
    }

    @Transactional
    public Page<DoResponse> searchPlan(String keyword, PageDTO pageDTO) {
        Pageable pageable = PageRequest.of(pageDTO.page(), pageDTO.pageSize(),
            Sort.by(Sort.Direction.DESC, "id"));

        Page<Do> searchedPlans = doRepository.findAllByDescriptionContains(keyword, pageable);

        return searchedPlans.map(DoResponse::from);
    }
}
