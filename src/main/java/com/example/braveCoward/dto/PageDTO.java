package com.example.braveCoward.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public record PageDTO(

    @Schema(description = "페이지 번호 (1부터 시작)", example = "1")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    int page,

    @Schema(description = "페이지에 들어있는 데이터 수", example = "10")
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    int pageSize
) {
}
