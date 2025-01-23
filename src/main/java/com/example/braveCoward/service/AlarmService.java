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

    public AlarmService
            (JavaMailSender mailSender,
             UserRepository userRepository,
             AlarmRepository alarmRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.alarmRepository = alarmRepository;
    }

    public String sendEmailToUser(Long userId, String description) {
        // userId로 User 조회
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        // 이메일 주소 확인
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return "Email not found";
        }

        String email = user.getEmail();

        try {
            //  이메일 발송
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(email);
            helper.setSubject("알림: " + description);
            helper.setText("안녕하세요, " + user.getName() + "님!\n\n" + description);

            mailSender.send(message);

            //  정보 저장
            Alarm alarm = Alarm.builder()
                    .description(description)
                    .user(user)
                    .build();

            alarmRepository.save(alarm);

            return "Email sent to: " + email + " and alarm saved with description: " + description;

        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed to send email to: " + email;
        }
    }
}