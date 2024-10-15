package com.example.braveCoward.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.Task;

public interface TaskRepository extends Repository<Task, Long> {
    Task save(Task task);

    void deleteById(Long taskId);

    List<Task> findAll();

    Optional<Task> findById(Long taskId);
}
