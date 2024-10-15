package com.example.braveCoward.repository;

import com.example.braveCoward.model.Do;
import org.springframework.data.repository.Repository;
import java.util.List;
import java.util.Optional;

public interface DoRepository extends Repository<Do, Long> {
    Do save(Do doEntity);

    void deleteById(Long doId);

    List<Do> findAll();

    Optional<Do> findById(Long doId);
}
