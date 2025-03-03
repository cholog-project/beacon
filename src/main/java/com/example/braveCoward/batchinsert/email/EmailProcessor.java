package com.example.braveCoward.batchinsert.email;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.User;

@Component
public class EmailProcessor implements ItemProcessor<Plan, String> {

    @Override
    public String process(Plan plan){
        User user = plan.getTeamMember().getUser();
        return user.getEmail();
    }
}
