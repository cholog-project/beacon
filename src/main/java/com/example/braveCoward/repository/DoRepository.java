package com.example.braveCoward.repository;

import com.example.braveCoward.model.Do;
import org.springframework.data.repository.Repository;
import java.util.List;


public interface DoRepository extends Repository<Do, Long> {
    Do save(Do doEntity);

    void deleteById(Long doId);

    List<Do> findAll();
}
