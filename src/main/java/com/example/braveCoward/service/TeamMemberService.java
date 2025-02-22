package com.example.braveCoward.service;

import com.example.braveCoward.dto.team.AddMemberRequest;
import com.example.braveCoward.dto.team.AddMemberResponse;
import com.example.braveCoward.exception.CustomException;
import com.example.braveCoward.exception.ErrorStatus;
import com.example.braveCoward.model.Team;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.TeamRepository;
import com.example.braveCoward.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddMemberResponse addMember(AddMemberRequest request) {
        Team team = teamRepository.findById(request.teamId())
            .orElseThrow(() -> new CustomException(ErrorStatus.TEAM_NOT_FOUND));

        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        TeamMember teamMember = TeamMember.builder()
            .role(request.role())
            .position(request.position())
            .team(team)
            .user(user)
            .build();

        TeamMember savedMember = teamMemberRepository.save(teamMember);

        return new AddMemberResponse(
            savedMember.getId(),
            savedMember.getRole(),
            savedMember.getPosition(),
            savedMember.getTeam().getId(),
            savedMember.getUser().getId());
    }
}
