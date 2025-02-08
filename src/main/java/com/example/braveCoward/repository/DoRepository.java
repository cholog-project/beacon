package com.example.braveCoward.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.braveCoward.model.Do;

public interface DoRepository extends JpaRepository<Do, Long> {
    Do save(Do doEntity);

    void deleteById(Long doId);

    List<Do> findAll();

    Optional<Do> findById(Long doId);

    @Modifying
    @Query("DELETE FROM Do d WHERE d.plan.id = :planId")
    void deleteByPlanId(@Param("planId") Long planId);

    List<Do> findAllByPlanId(Long planId);

    Page<Do> findAllByDescriptionContains(String keyword, Pageable pageable);
}
