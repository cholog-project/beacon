package com.example.braveCoward.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void doSomething() {
        logger.info("INFO 레벨 로그입니다.");
        logger.debug("DEBUG 레벨 로그입니다.");
        logger.warn("WARN 레벨 로그입니다.");
        logger.error("ERROR 레벨 로그입니다.");
    }
}
