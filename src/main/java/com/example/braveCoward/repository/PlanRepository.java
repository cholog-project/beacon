package com.example.braveCoward.repository;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.Project;

public interface PlanRepository extends Repository<Plan, Long> {
    Plan save(Plan plan);

    void deleteById(Long planId);
}
