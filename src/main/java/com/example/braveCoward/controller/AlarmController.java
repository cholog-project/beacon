package com.example.braveCoward.controller;

import com.example.braveCoward.service.AlarmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping("/send-email")
    public String sendEmailToUser(@RequestParam Long userId, @RequestParam String description) {
        return alarmService.sendEmailToUser(userId, description);
    }
}

