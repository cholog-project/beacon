package com.example.braveCoward.service;

import com.example.braveCoward.model.Plan;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlanNotificationScheduler {
    private final PlanRepository planRepository;
    private final AlarmService alarmService;

    private static final List<Plan.Status> VALID_STATUSES = Arrays.asList(Plan.Status.NOT_STARTED, Plan.Status.IN_PROGRESS);
    //24:00에 plan조회해서 원하는 마감 조건, 상태일 경우 plan등록 유저의 이메일로 전송
    @Scheduled(cron = "0 00 24 * * ?")
    public void sendPlanReminderEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // 마감일이 내일인 Plan 조회
        List<Plan> plansDueTomorrow = planRepository.findByEndDateAndStatusIn(tomorrow, VALID_STATUSES);
        for (Plan plan : plansDueTomorrow) {
            User user = plan.getTeamMember().getUser();
            String description = "🚨 Plan '" + plan.getTitle() + "' 이(가) " + plan.getEndDate() + " 마감됩니다.";
            alarmService.sendEmailToUser(user, description);
        }
    }

    // 마감일 당일 알림
    @Scheduled(cron = "0 00 24 * * ?")
    public void sendPlanDueTodayEmails() {
        LocalDate today = LocalDate.now();
        List<Plan> plansDueToday = planRepository.findByEndDateAndStatusIn(today, VALID_STATUSES);

        for (Plan plan : plansDueToday) {
            User user = plan.getTeamMember().getUser();
            String description = "🚨 오늘이 Plan '" + plan.getTitle() + "' 마감일입니다! 기한 내에 처리해주세요.";
            alarmService.sendEmailToUser(user, description);
        }
    }

    //마감 하루 후
    @Scheduled(cron = "0 00 24 * * ?")
    public void sendPlanOverdueEmails() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Plan> overduePlans = planRepository.findByEndDateAndStatusIn(yesterday, VALID_STATUSES);

        for (Plan plan : overduePlans) {
            User user = plan.getTeamMember().getUser();
            String description = "⚠️ Plan '" + plan.getTitle() + "' 마감일이 **지났습니다**. 빠르게 처리해주세요!";
            alarmService.sendEmailToUser(user, description);
        }
    }



}