package com.example.braveCoward.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.MembersResponse;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TeamMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final ProjectRepository projectRepository;

    public MembersResponse getTeamMembers(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        List<TeamMember> teamMembers = project.getTeam().getTeamMembers();

        return MembersResponse.from(teamMembers);
    }
}
