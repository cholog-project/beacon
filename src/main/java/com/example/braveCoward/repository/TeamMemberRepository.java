package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;

public interface TeamMemberRepository extends CrudRepository<TeamMember, Long> {
    Optional<TeamMember> findById(Long aLong);

    TeamMember save(TeamMember teamMember);

    Optional<TeamMember> findByTeamAndUser(Team team, User user);
}
