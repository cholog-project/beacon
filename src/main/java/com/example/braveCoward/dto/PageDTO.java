package com.example.braveCoward.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PageDTO(

    @Schema(description = "페이지 번호 (0부터 시작)", example = "1")
    int page,

    @Schema(description = "페이지에 들어있는 데이터 수", example = "10")
    int pageSize
) {
}
