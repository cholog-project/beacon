package com.example.braveCoward.service;

import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;
import com.example.braveCoward.model.Do;
import com.example.braveCoward.model.Task;
import com.example.braveCoward.repository.DoRepository;
import com.example.braveCoward.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoService {

    private final TaskRepository taskRepository;
    private final DoRepository doRepository;

    public CreateDoResponse createDo(Long taskId, CreateDoRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new IllegalArgumentException("테스크를 찾을 수 없습니다."));

        Do doEntity = Do.builder()
                .date(request.date())
                .status(request.status())
                .description(request.description())
                .task(task)
                .build();

        Do savedDo = doRepository.save(doEntity);

        return CreateDoResponse.from(savedDo);
    }

    public void deleteDo(Long doId) {
        doRepository.deleteById(doId);
    }

    public DosResponse getDos(Long taskId) {
        List<DoResponse> doResponses = doRepository.findAll().stream()
                .map(doEntity -> new DoResponse(
                        doEntity.getId(),
                        doEntity.getDate(),
                        doEntity.getStatus(),
                        doEntity.getDescription(),
                        taskId
        ))
                .toList();

        int totalCount = doResponses.size();
        return new DosResponse(totalCount, doResponses);
    }
}
