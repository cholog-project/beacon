package com.example.braveCoward.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import com.example.braveCoward.repository.AlarmRepository;
import com.example.braveCoward.model.Alarm;
import com.example.braveCoward.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final JavaMailSender mailSender;
    private final AlarmRepository alarmRepository;

    public String sendEmailToUser(User user, String description) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return "이메일이 없습니다.";
        }

        if (sendEmail(user, description)) {
            saveAlarm(user, description);
            return "이메일이 성공적으로 전송되었습니다.";
        } else {
            return "이메일 발송 실패";
        }
    }

    private boolean sendEmail(User user, String description) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("📢 Plan 마감 알림");
            helper.setText("안녕하세요, " + user.getName() + "님!\n\n" +
                    "Plan '" + description + "' 이(가) 내일 마감됩니다. \n\n" +
                    "기한 내에 확인해주세요!");

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