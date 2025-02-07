package com.example.braveCoward.dto.Do;

import com.example.braveCoward.model.Do;

import java.time.LocalDate;

public record DoResponse(
    Long id,
    LocalDate date,
    String description,
    Long planId
) {

    public static DoResponse from(Do doEntity) {
        return new DoResponse(
            doEntity.getId(),
            doEntity.getDate(),
            doEntity.getDescription(),
            doEntity.getPlan().getId()
        );
    }
}
