package com.example.braveCoward.repository;

import com.example.braveCoward.model.Do;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface DoRepositoryCustom {
    Page<Do> searchByDescriptionContains(Long projectId, String keyword, Pageable pageable);
    Page<Do> searchByDescriptionStartsWith(Long projectId, String keyword, Pageable pageable);
}
