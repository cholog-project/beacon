package com.example.braveCoward.dto.team;

import java.util.List;

public record TeamCreateRequest(
        String name,
        String description,  // 팀 설명 추가
        Long projectId,
        List<String> memberEmails
) {}