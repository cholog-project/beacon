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

public interface DoRepository extends JpaRepository<Do, Long>, DoRepositoryCustom  {
    Do save(Do doEntity);

    void deleteById(Long doId);

    List<Do> findAll();

    Optional<Do> findById(Long doId);

    @Modifying
    @Query("DELETE FROM Do d WHERE d.plan.id = :planId")
    void deleteByPlanId(@Param("planId") Long planId);

    Page<Do> findAllByPlanId(Long planId, Pageable pageable);

    Page<Do> findAllByDescriptionContainsAndProjectId(String keyword, Long projectId, Pageable pageable);

    @Query("SELECT d FROM Do d WHERE d.description LIKE CONCAT(:keyword, '%') AND d.project.id = :projectId ORDER BY d.id DESC")
    Page<Do> findAllByDescriptionStartsWithAndProjectId(@Param("keyword") String keyword,
                                                        @Param("projectId") Long projectId,
                                                        Pageable pageable);
    @Query(
            value = "SELECT d.id as id, d.date as date, d.description as description, d.plan_id as planId " +
                    "FROM do d " +
                    "WHERE MATCH(d.description) AGAINST(:keyword IN BOOLEAN MODE) " +
                    "AND d.project_id = :projectId " +
                    "ORDER BY d.id DESC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM do d " +
                    "WHERE MATCH(d.description) AGAINST(:keyword IN BOOLEAN MODE) " +
                    "AND d.project_id = :projectId",
            nativeQuery = true)
    Page<DoProjection> searchDoFullText(@Param("keyword") String keyword,
                                        @Param("projectId") Long projectId,
                                        Pageable pageable);
}
