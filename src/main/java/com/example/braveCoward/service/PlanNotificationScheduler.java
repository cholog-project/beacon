package com.example.braveCoward.service;

import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlanNotificationScheduler {
    private final PlanRepository planRepository;
    private final AlarmService alarmService;

    @Scheduled(cron = "0 45 17 * * ?")  // 매일 자정 실행
    public void sendPlanReminderEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // 마감일이 내일인 Plan 조회
        List<Plan> plansDueTomorrow = planRepository.findByEndDate(tomorrow);

        for (Plan plan : plansDueTomorrow) {
            User user = plan.getTeamMember().getUser();
            String description = "Plan '" + plan.getTitle() + "' 이(가) " + plan.getEndDate() + " 마감됩니다.";

            alarmService.sendEmailToUser(user, description);
        }
    }
}