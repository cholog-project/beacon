package com.example.braveCoward.repository;

import com.example.braveCoward.model.Do;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoRepository extends JpaRepository<Do, Long> {
    Do save(Do doEntity);

    void deleteById(Long doId);

    List<Do> findAll();

    Optional<Do> findById(Long doId);

    @Modifying
    @Query("DELETE FROM Do d WHERE d.task.id = :taskId")
    void deleteByTaskId(@Param("taskId") Long taskId);

    List<Do> findAllByTaskId(Long taskId);

}
