package com.example.braveCoward.batchinsert;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DataInsertRunner implements CommandLineRunner {
    private final DataInsertService dataInsertService;

    @Override
    public void run(String... args) {
        Instant start = Instant.now(); // ✅ 시작 시간 기록

        dataInsertService.insertLargeData(1743); // 1743개의 팀 → 100만 개 이상의 데이터 삽입

        Instant end = Instant.now(); // ✅ 종료 시간 기록
        Duration duration = Duration.between(start, end); // ✅ 실행 시간 계산

        long durationMs = duration.toMillis();
        double durationSec = durationMs / 1000.0;

        System.out.println("✅ 데이터 삽입 완료! 실행 시간: " + durationMs + " ms (" + durationSec + " 초)");
    }
}
