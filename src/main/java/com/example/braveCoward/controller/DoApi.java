package com.example.braveCoward.controller;

import com.example.braveCoward.dto.CreateDoRequest;
import com.example.braveCoward.dto.CreateDoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/projects/tasks")
@Tag(name = "(Normal) Do", description = "Do 관련 API")
public interface DoApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @Operation(summary = "Do 추가")
    @PostMapping("/{taskId}/dos")
    ResponseEntity<CreateDoResponse> createDo(
            @PathVariable Long taskId,
            @Valid @RequestBody CreateDoRequest request
    );
}
