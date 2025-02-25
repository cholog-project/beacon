package com.example.braveCoward.global.exectime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(com.example.braveCoward.global.exectime.ExecutionTimeLogger)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("⏳ 실행시간 - {}: {} ms", joinPoint.getSignature(), (end - start));
        return result;
    }
}
