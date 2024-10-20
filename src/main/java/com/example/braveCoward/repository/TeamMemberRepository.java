package com.example.braveCoward.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;

public interface TeamMemberRepository extends Repository<TeamMember, Integer> {
    Optional<TeamMember> findById(Long aLong);

    TeamMember save(TeamMember teamMember);

    Optional<TeamMember> findByTeamAndUser(Team team, User user);
}
