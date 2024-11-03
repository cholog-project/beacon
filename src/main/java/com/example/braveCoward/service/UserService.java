package com.example.braveCoward.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.braveCoward.dto.MembersResponse;
import com.example.braveCoward.dto.UserLoginRequest;
import com.example.braveCoward.dto.UserLoginResponse;
import com.example.braveCoward.model.Project;
import com.example.braveCoward.model.TeamMember;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.ProjectRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import com.example.braveCoward.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public MembersResponse getTeamMembers(Long projectId) {
        Project project = projectRepository.findById(projectId).get();
        List<TeamMember> teamMembers = project.getTeam().getTeamMembers();

        return MembersResponse.from(teamMembers);
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email()).get();


    }
}
