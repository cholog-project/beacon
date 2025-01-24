package com.example.braveCoward.service;

import com.example.braveCoward.model.Alarm;
import com.example.braveCoward.model.User;
import com.example.braveCoward.repository.AlarmRepository;
import com.example.braveCoward.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AlarmService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    public AlarmService(JavaMailSender mailSender,
                        UserRepository userRepository,
                        AlarmRepository alarmRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.alarmRepository = alarmRepository;
    }

    //검증 및 보내기
    public String sendEmailToUser(Long userId, String description) {
        // 사용자 조회 및 이메일 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found "));

        if (!isValidEmail(user.getEmail())) {
            throw new RuntimeException("Email not found " );
        }

        // 이메일 발송
        if (!sendEmail(user, description)) {
            throw new RuntimeException("Failed to send email");
        }

        // 알림 저장
        saveAlarm(user, description);
        return "Email sent to: " + user.getEmail() + " and alarm saved with description: " + description;
    }

    //세부적인 검증 내용
    private boolean isValidEmail(String email) {
        return email != null && !email.isEmpty();
    }

    private boolean sendEmail(User user, String description) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("알림: " + description);
            helper.setText("안녕하세요, " + user.getName() + "님!\n\n" + description);

            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveAlarm(User user, String description) {
        Alarm alarm = Alarm.builder()
                .description(description)
                .user(user)
                .build();

        alarmRepository.save(alarm);
    }
}