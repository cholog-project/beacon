package com.example.braveCoward.controller;

import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.UserRepository;
import com.example.braveCoward.service.AlarmService;
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
    private final UserRepository userRepository;

    @GetMapping("/send-email")
    public String sendEmailToUser(@RequestParam Long userId, @RequestParam String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자가 없습니다."));

        return alarmService.sendEmailToUser(user, description);
    }
}