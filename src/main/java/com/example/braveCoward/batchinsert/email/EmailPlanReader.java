package com.example.braveCoward.batchinsert.email;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import com.example.braveCoward.model.Plan;
import com.example.braveCoward.repository.PlanRepository;

@Component
@StepScope
public class EmailPlanReader implements ItemReader<Plan> {
    private final Iterator<Plan> planIterator;
    private static final List<Plan.Status> VALID_STATUSES = Arrays.asList(Plan.Status.NOT_STARTED,
        Plan.Status.IN_PROGRESS);

    public EmailPlanReader(PlanRepository planRepository){
        long start = System.currentTimeMillis();
        List<Plan> plans = planRepository.findPlansWithUsers(LocalDate.now(), VALID_STATUSES);
        System.out.println(plans.size());
        long end = System.currentTimeMillis();
        System.out.println("plan 조회 시간 : " + (end - start) + "ms");

        this.planIterator = plans.iterator();
    }

    @Override
    public Plan read(){
        return planIterator.hasNext() ? planIterator.next() :null;
    }
}
