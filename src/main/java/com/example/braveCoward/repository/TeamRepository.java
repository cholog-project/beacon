package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.braveCoward.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team save(Team team);

    Optional<Team> findByName(String 준기팀);
}
