package com.example.braveCoward.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailTestService {

    private final JavaMailSender mailSender;

    public EmailTestService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTestEmail(String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("테스트 이메일");
            helper.setText("안녕하세요! 이메일 발송이 성공적으로 작동합니다.");

            mailSender.send(message);
            System.out.println("이메일 발송 성공: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("이메일 발송 실패: " + e.getMessage());
        }
    }
}