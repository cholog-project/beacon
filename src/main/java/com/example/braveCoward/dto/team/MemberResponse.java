package com.example.braveCoward.dto.team;

import java.time.LocalDateTime;

public record MemberResponse(
        Integer memberId,
        String email,
        LocalDateTime joinedAt
) {}