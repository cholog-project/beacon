package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.Project;

public interface ProjectRepository extends Repository<Project, Long> {
    Optional<Project> findById(Long projectId);

    Project save(Project project);
}
