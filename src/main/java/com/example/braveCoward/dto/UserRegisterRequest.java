package com.example.braveCoward.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserRegisterRequest(
    @Schema(description = "이메일", example = "cholog@naver.com", requiredMode = REQUIRED)
    @Email(message = "이메일 형식을 지켜주세요. ${validatedValue}")
    @NotBlank(message = "이메일을 입력해주세요.")
    String email,

    @Schema(description = "이름", example = "초록", requiredMode = REQUIRED)
    @Size(max = 50, message = "이름은 50자 이내여야 합니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]+$", message = "이름은 한글, 영문만 사용할 수 있습니다.")
    String name,

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password
) {

}
