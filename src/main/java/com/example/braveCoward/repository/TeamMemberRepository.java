package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.TeamMember;

public interface TeamMemberRepository extends Repository<TeamMember, Integer> {
    Optional<TeamMember> findById(Long aLong);

    TeamMember save(TeamMember teamMember);
}
