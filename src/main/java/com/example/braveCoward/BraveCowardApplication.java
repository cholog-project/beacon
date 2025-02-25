package com.example.braveCoward;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableBatchProcessing
public class BraveCowardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BraveCowardApplication.class, args);
    }
}
