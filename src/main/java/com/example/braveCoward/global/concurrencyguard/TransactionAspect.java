package com.example.braveCoward.global.concurrencyguard;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionAspect {
    // leaseTime보다 트랜잭션 타임아웃은 작아야 한다.
    // leaseTimeOut 발생 전에 rollback 시키기 위함
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 2)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
