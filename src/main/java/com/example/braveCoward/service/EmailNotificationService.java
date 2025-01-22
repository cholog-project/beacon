package com.example.braveCoward.service;

import com.example.braveCoward.model.Plan;
import com.example.braveCoward.repository.PlanRepository;
import com.example.braveCoward.repository.TeamMemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailNotificationService {

    private final PlanRepository planRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final JavaMailSender mailSender;

    public EmailNotificationService(PlanRepository planRepository, TeamMemberRepository teamMemberRepository, JavaMailSender mailSender) {
        this.planRepository = planRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.mailSender = mailSender;
    }

//    @Scheduled(cron = "0 0 9 * * ?") // 매일 오전 9시에 실행
    public void sendDeadlineNotifications() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Plan> plans = planRepository.findPlansEndingTomorrow(tomorrow);

        for (Plan plan : plans) {
            String email = plan.getTeamMember().getUser().getEmail();
            String subject = "[알림] 일정 마감이 하루 남았습니다!";
            String message = String.format("안녕하세요, %s님!\n\n"
                            + "일정 \"%s\"의 마감일이 하루 남았습니다.\n"
                            + "마감일: %s\n\n"
                            + "확인 부탁드립니다!",
                    plan.getTeamMember().getUser().getName(),
                    plan.getTitle(),
                    plan.getEndDate());

            sendEmail(email, subject, message);
        }
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // 실제 서비스에서는 로깅 처리 필요
        }
    }
}