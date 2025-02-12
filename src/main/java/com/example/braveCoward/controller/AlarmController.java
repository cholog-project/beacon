package com.example.braveCoward.controller;

import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.UserRepository;
import com.example.braveCoward.service.AlarmService;
import com.example.braveCoward.service.PlanNotificationScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final PlanNotificationScheduler planNotificationScheduler;

    @GetMapping("/send-test-email")
    public String sendScheduledEmailNow() {
        planNotificationScheduler.sendPlanNotifications();
        return "스케줄링된 이메일이 즉시 실행되었습니다.";
    }
}