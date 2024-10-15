package com.example.braveCoward.dto.Do;

import java.util.List;

public record DosResponse(
        int totalCount,
        List<DoResponse> dos
) {
}
