package com.example.braveCoward.controller;

import com.example.braveCoward.service.EmailTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestController {

    private final EmailTestService emailTestService;

    public EmailTestController(EmailTestService emailTestService) {
        this.emailTestService = emailTestService;
    }

    @GetMapping("/test-email")
    public String sendTestEmail(@RequestParam String email) {
        emailTestService.sendTestEmail(email);
        return "테스트 이메일이 " + email + "로 발송되었습니다.";
    }
}