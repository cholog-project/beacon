package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.Plan;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanRepository extends Repository<Plan, Long> {
    Plan save(Plan plan);

    void deleteById(Long planId);

    Optional<Plan> findById(Long planId);

    void delete(Plan plan);
    @Query("SELECT p FROM Plan p WHERE p.endDate = :targetDate AND p.status IN :statuses")
    List<Plan> findByEndDateAndStatusIn(@Param("targetDate") LocalDate targetDate, @Param("statuses") List<Plan.Status> statuses);

    Page<Plan> findAllByProjectId(Long projectId, Pageable pageable);

    List<Plan> findByEndDate(LocalDate endDate);

    Page<Plan> findAllByTitleContainsOrDescriptionContains(String keyword, String keyword2, Pageable pageable);

    Page<Plan> findAllByTitleContains(String keyword, Pageable pageable);

    Page<Plan> findAllByDescriptionContains(String keyword, Pageable pageable);
}
