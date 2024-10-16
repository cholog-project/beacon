package com.example.braveCoward.repository;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.Team;

public interface TeamRepository extends Repository<Team, Long> {
    Team save(Team team);
}
