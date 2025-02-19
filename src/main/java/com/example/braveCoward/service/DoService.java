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
import com.example.braveCoward.model.Do;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.repository.DoRepository;
import com.example.braveCoward.repository.PlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoService {

    private final DoRepository doRepository;
    private final PlanRepository planRepository;

    @Transactional(readOnly = false)
    public CreateDoResponse createDo(Long planId, CreateDoRequest request) {

        Plan plan = planRepository.findById(planId)
            .orElseThrow(() -> new IllegalArgumentException("Plan을 찾을 수 없습니다."));

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
        Do deletedDo = doRepository.findById(doId)
            .orElseThrow(() -> new IllegalArgumentException("Do를 찾을 수 없습니다."));

        doRepository.deleteById(doId);
    }

    public DosResponse getDos(Long planId, PageDTO pageDTO) {
        Pageable pageable = PageRequest.of(pageDTO.page(), pageDTO.pageSize(),
            Sort.by(Sort.Direction.DESC, "id"));

        List<DoResponse> doResponses = doRepository.findAllByPlanId(planId, pageable).stream()
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
    public Page<DoResponse> searchDo(String keyword, Long projectId, PageDTO pageDTO) {
        Pageable pageable = PageRequest.of(pageDTO.page() - 1, pageDTO.pageSize(),
            Sort.by(Sort.Direction.DESC, "id"));

        Page<Do> searchedDos = doRepository.findAllByDescriptionContainsAndProjectId(keyword, projectId, pageable);

        return searchedDos.map(DoResponse::from);
    }

    @Transactional
    public void completeDo(Long doId) {
        Do completeDo = doRepository.findById(doId)
            .orElseThrow(() -> new IllegalArgumentException("Do를 찾을 수 없습니다."));

        completeDo.setCompleted(!completeDo.isCompleted());
        doRepository.save(completeDo);

        Plan plan = completeDo.getPlan();
        List<Do> dos = plan.getDos();

        if (dos.stream().allMatch(Do::isCompleted)) {
            plan.setStatus(Plan.Status.COMPLETED);
        }
    }
}
