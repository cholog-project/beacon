package com.example.braveCoward.dto.Do;

import com.example.braveCoward.model.Do;

import java.time.LocalDate;

public record CreateDoResponse(
        LocalDate date,
        Do.Status status,
        String description,
        Long taskId
) {

    // 그냥 from(Do do)로 하면 do 자체가 Java의 예약어기 때문에 오류 발생
    public static CreateDoResponse from(Do doEntity) {
        return new CreateDoResponse(
            doEntity.getDate(),
            doEntity.getStatus(),
            doEntity.getDescription(),
            doEntity.getTask().getId()
        );
    }
}