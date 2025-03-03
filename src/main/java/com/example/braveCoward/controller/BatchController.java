package com.example.braveCoward.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job emailJob;

    public BatchController(JobLauncher jobLauncher, Job emailJob) {
        this.jobLauncher = jobLauncher;
        this.emailJob = emailJob;
    }

    @GetMapping("/sendEmails")
    public String sendEmails() {
        try {
            JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(emailJob, params);
            log.info("📨 이메일 전송 Batch 실행됨. 실행 ID: {}", jobExecution.getId());

            return "✅ Spring Batch Job 실행됨! (Execution ID: " + jobExecution.getId() + ")";
        } catch (Exception e) {
            log.error("배치 실행 실패: {}", e.getMessage());
            return "Spring Batch 실행 실패: " + e.getMessage();
        }
    }
}