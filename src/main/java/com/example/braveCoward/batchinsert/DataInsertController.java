package com.example.braveCoward.batchinsert;

import java.time.Duration;
import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.swagger.DataInsertApi;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataInsertController implements DataInsertApi {
    private final DataInsertService dataInsertService;

    @PostMapping("batch-insert")
    public ResponseEntity<String> insertData(@RequestParam(defaultValue = "1743") int numTeams) {
        Instant start = Instant.now(); // ✅ 시작 시간 기록

        dataInsertService.insertLargeData(numTeams);

        Instant end = Instant.now(); // ✅ 종료 시간 기록
        Duration duration = Duration.between(start, end); // ✅ 실행 시간 계산

        long durationMs = duration.toMillis();
        double durationSec = durationMs / 1000.0;

        return ResponseEntity.ok("✅ 데이터 삽입 완료! 실행 시간: " + durationMs + " ms (" + durationSec + " 초)");
    }
}
