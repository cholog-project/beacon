package com.example.braveCoward.dto.Do;

import com.example.braveCoward.model.Do;

import java.time.LocalDate;

public record DoResponse(
        Long id,
        LocalDate date,
        Do.Status status,
        String description,
        Long taskId
) {

    public static DoResponse from(Do doEntity) {
        return new DoResponse(
                doEntity.getId(),
                doEntity.getDate(),
                doEntity.getStatus(),
                doEntity.getDescription(),
                doEntity.getPlan().getId()
        );
    }
}
