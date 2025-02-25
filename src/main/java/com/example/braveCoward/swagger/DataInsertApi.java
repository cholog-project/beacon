package com.example.braveCoward.swagger;

import com.example.braveCoward.global.exectime.ExecutionTimeLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/data")
@Tag(name = "(Normal) Data", description = "Batch-Insert 관련 API")
public interface DataInsertApi {

    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
        }
    )
    @Operation(summary = "100만 데이터 집어넣기")
    @ExecutionTimeLogger
    @PostMapping("batch-insert")
    ResponseEntity<String> insertData(
        @RequestParam(defaultValue = "1743") int numTeams
    );
}
