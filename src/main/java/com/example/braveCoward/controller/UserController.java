package com.example.braveCoward.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.dto.MembersResponse;
import com.example.braveCoward.dto.UserLoginRequest;
import com.example.braveCoward.dto.UserLoginResponse;
import com.example.braveCoward.service.UserService;
import com.example.braveCoward.swagger.UserApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @GetMapping("/projects/{projectId}/members")
    public ResponseEntity<MembersResponse> getMembers(
        @PathVariable Long projectId
    ) {
        MembersResponse response = userService.getTeamMembers(projectId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserLoginResponse> login(
        @RequestBody @Valid UserLoginRequest request
    ) {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.created(URI.create("/"))
            .body(response);
    }
}
