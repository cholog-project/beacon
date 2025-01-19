package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.braveCoward.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findById(Long projectId);
    Project save(Project project);
    void delete(Project project);
}