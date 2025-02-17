package com.example.braveCoward.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long> {
    Optional<TeamMember> findById(Long aLong);

    TeamMember save(TeamMember teamMember);

    List<TeamMember> findByTeamId(Team team);

    void deleteByTeamId(Long teamId);
}
