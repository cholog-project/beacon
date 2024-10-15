package com.example.braveCoward.repository;

import com.example.braveCoward.model.Do;
import org.springframework.data.repository.Repository;

public interface DoRepository extends Repository<Do, Long> {
    Do save(Do doEntity);

    void deleteById(Long doId);
}
