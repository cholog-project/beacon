package com.example.braveCoward.mock;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MockEmailService {
    private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);

    @Async ("emailBatchExecutor")
    public void sendMockEmail(String email) {
        try {
            TimeUnit.MILLISECONDS.sleep(4500); // 4.5초 대기
            logger.info("Mock 이메일 전송 완료: {}", email);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("이메일 전송 실패: {}", email, e);
        }
    }
}
