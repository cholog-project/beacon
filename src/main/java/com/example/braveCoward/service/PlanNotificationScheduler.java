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

    private static final List<Plan.Status> VALID_STATUSES = Arrays.asList(Plan.Status.NOT_STARTED,
        Plan.Status.IN_PROGRESS);

    // 알림 규칙을 정의한 내부 클래스를 사용하여 날짜 + 메시지를 한 번에 처리
    private static class NotificationRule {
        private final int daysOffset;
        private final String messageTemplate;

        public NotificationRule(int daysOffset, String messageTemplate) {
            this.daysOffset = daysOffset;
            this.messageTemplate = messageTemplate;
        }
    }

    // 알림 규칙 리스트 (하루 전, 당일, 하루 후)
    private static final List<NotificationRule> NOTIFICATION_RULES = Arrays.asList(
        new NotificationRule(1, "🚨 Plan '{title}' 이(가) {date} 마감됩니다."), // 하루 전
        new NotificationRule(0, "🚨 오늘이 Plan '{title}' 마감일입니다! 기한 내에 처리해주세요."), // 당일
        new NotificationRule(-1, "⚠️ Plan '{title}' 마감일이 **지났습니다**. 빠르게 처리해주세요!") // 하루 후
    );

    // plan 마감 알림 전송 공통 메서드
    private void sendEmailNotification(LocalDate date, String messageTemplate) {
        List<Plan> plans = planRepository.findByEndDateAndStatusIn(date, VALID_STATUSES);

        for (Plan plan : plans) {
            User user = plan.getTeamMember().getUser();
            String description = messageTemplate
                .replace("{title}", plan.getTitle())
                .replace("{date}", plan.getEndDate().toString());

            alarmService.sendEmailToUser(user, description);
        }
    }

    // 하루 전, 당일, 하루 후 알림을 한 번의 Scheduled Task로 처리
    @Scheduled(cron = "0 35 21 * * ?") // 매일 24:00 실행
    public void sendPlanNotifications() {
        LocalDate today = LocalDate.now();

        for (NotificationRule rule : NOTIFICATION_RULES) {
            sendEmailNotification(today.plusDays(rule.daysOffset), rule.messageTemplate);
        }
    }
}