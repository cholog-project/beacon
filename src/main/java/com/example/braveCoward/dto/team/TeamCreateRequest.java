package com.example.braveCoward.dto.team;

import java.util.List;

public record TeamCreateRequest(
        String name,
        Long projectId,
        List<String> memberEmails
) {}