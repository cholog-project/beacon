package com.example.braveCoward.swagger;

import com.example.braveCoward.dto.Do.ChangeDoRequest;
import com.example.braveCoward.dto.Do.CreateDoRequest;
import com.example.braveCoward.dto.Do.CreateDoResponse;
import com.example.braveCoward.dto.Do.DoResponse;
import com.example.braveCoward.dto.Do.DosResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/dos")
@Tag(name = "(Normal) Do", description = "Do 관련 API")
public interface DoApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 추가")
    @PostMapping("/new/{planId}")
    ResponseEntity<CreateDoResponse> createDo(
        @PathVariable Long planId,
        @Valid @RequestBody CreateDoRequest request
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 삭제")
    @DeleteMapping("/{doId}")
    ResponseEntity<Void> deleteDo(
        @PathVariable Long doId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 목록 조회")
    @GetMapping("/plan/{planId}")
    ResponseEntity<DosResponse> getDoList(
        @PathVariable Long planId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 단일 조회")
    @GetMapping("/{doId}")
    ResponseEntity<DoResponse> getDo(
        @PathVariable Long doId
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "모든 Do 조회")
    @GetMapping("/dos")
    ResponseEntity<DosResponse> getAllDo();

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 수정")
    @PatchMapping("/{doId}")
    ResponseEntity<Void> changeDo(Long doId, ChangeDoRequest request);
}
