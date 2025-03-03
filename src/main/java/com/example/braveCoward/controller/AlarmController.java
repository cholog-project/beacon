package com.example.braveCoward.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.braveCoward.mock.MockEmailService;
import com.example.braveCoward.service.AlarmService;
import com.example.braveCoward.service.PlanNotificationScheduler;

import lombok.RequiredArgsConstructor;

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

    @GetMapping("/send-one-email")
    public String sendOneEmail() {
        planNotificationScheduler.sendOneEmail();
        return "스케줄링된 이메일이 즉시 실행되었습니다.";
    }

    @GetMapping("/send-ten-email")
    public String sendtenEmail() {
        planNotificationScheduler.sendTenEmail();
        return "스케줄링된 이메일이 즉시 실행되었습니다.";
    }

    @GetMapping("/send-mock-email")
    public void sendMockEmailNotification(){
        planNotificationScheduler.sendMockEmailNotification();
    }
}

