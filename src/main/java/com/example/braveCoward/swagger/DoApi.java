package com.example.braveCoward.swagger;

import com.example.braveCoward.global.exectime.ExecutionTimeLogger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import jakarta.validation.constraints.Min;

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
    @ExecutionTimeLogger
    @GetMapping("/plan/{planId}")
    ResponseEntity<Page<DoResponse>> getDoList(
        @PathVariable Long planId,
        @RequestParam(defaultValue = "1") @Min(1) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 단일 조회")
    @ExecutionTimeLogger
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

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 검색")
    @ExecutionTimeLogger
    @GetMapping("/search")
    ResponseEntity<Page<DoResponse>> searchDo(
        @RequestParam String keyword,
        @RequestParam Long projectId,
        @RequestParam(defaultValue = "1") @Min(1) int page,
        @RequestParam(defaultValue = "10") @Min(1) int size
    );


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @Operation(summary = "Do QueryDSL 검색")
    @ExecutionTimeLogger
    @GetMapping("/searchQueryDSL")
    ResponseEntity<Page<DoResponse>> searchDoWithQueryDSL(
            @RequestParam String keyword,
            @RequestParam Long projectId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size
    );

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "앞쪽 일치 검색 결과",
                    content = @Content(schema = @Schema(implementation = DoResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
    })
    @Operation(summary = "Do 검색 (앞쪽 일치)")
    @ExecutionTimeLogger
    @GetMapping("/searchStartsWith")
    ResponseEntity<Page<DoResponse>> searchDoStartsWith(
            @RequestParam String keyword,
            @RequestParam Long projectId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "FullText 검색 결과",
                            content = @Content(schema = @Schema(implementation = DoResponse.class))),
                    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @Operation(summary = "Do 검색 (FullText)")
    @ExecutionTimeLogger
    @GetMapping("/searchFullText")
    ResponseEntity<Page<DoResponse>> searchDoFullText(
            @RequestParam String keyword,
            @RequestParam Long projectId,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size
    );

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "Do 완료 여부 변경", description = "모든 Do가 완료되면 해당 Plan의 상태 COMPLETED로 변경")
    @PatchMapping("/complete/{doId}")
     ResponseEntity<Void> completeDo(
        @PathVariable Long doId
    );
}
