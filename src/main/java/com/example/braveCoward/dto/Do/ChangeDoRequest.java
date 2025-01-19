package com.example.braveCoward.dto.Do;

import java.time.LocalDate;

public record ChangeDoRequest(
    String description,
    LocalDate date
) {
}
