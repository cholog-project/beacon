package com.example.braveCoward.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;
import com.example.braveCoward.model.Do;
import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.Task;
import com.example.braveCoward.repository.DoRepository;
import com.example.braveCoward.repository.PlanRepository;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TaskRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;

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

    public DosResponse getDos(Long taskId) {
        List<DoResponse> doResponses = doRepository.findAllByTaskId(taskId).stream()
            .map(doEntity -> new DoResponse(
                doEntity.getId(),
                doEntity.getDate(),
                doEntity.getDescription(),
                taskId
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

    public DosResponse getAllDo(){
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
}
