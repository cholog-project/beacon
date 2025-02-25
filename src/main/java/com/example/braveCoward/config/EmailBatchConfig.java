package com.example.braveCoward.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.braveCoward.model.Plan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
public class EmailBatchConfig {

    private final String JOB_NAME = "emailJob";
    private final String STEP_NAME = "emailStep";

    @Bean
    public Job emailJob(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        Step emailStep
    ) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(emailStep)
            .build();
    }

    @Bean
    @JobScope
    public Step emailStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Plan> emailReader,
        ItemProcessor<Plan, String> emailProcessor,
        ItemWriter<String> emailWriter,
        @Qualifier("emailBatchExecutor") TaskExecutor emailBatchExecutor
    ) {
        return new StepBuilder(STEP_NAME, jobRepository)
            .<Plan, String>chunk(100, transactionManager) // 10개씩 처리
            .reader(emailReader)
            .processor(emailProcessor)
            .writer(emailWriter)
            .taskExecutor(emailBatchExecutor) // 병렬 처리 적용
            .build();
    }

    @Bean(name = "emailBatchExecutor")
    public TaskExecutor emailBatchExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(400);
        executor.setThreadNamePrefix("EmailBatchThread-");
        executor.initialize();
        return executor;
    }
}
