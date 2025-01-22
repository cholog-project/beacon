package com.example.braveCoward.controller;

import com.example.braveCoward.service.EmailNotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final EmailNotificationService emailNotificationService;

    public NotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    // 수동으로 알림 실행
    @GetMapping("/trigger")
    public String triggerNotifications() {
        emailNotificationService.sendDeadlineNotifications();
        return "알림이 발송되었습니다!";
    }
}

