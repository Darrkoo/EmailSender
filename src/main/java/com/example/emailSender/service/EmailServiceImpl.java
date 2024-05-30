package com.example.emailSender.service;

import com.example.emailSender.exception.ResourceNotFoundException;
import com.example.emailSender.model.EmailLog;
import com.example.emailSender.model.EmailSchedule;
import com.example.emailSender.model.EmailTemplate;
import com.example.emailSender.repo.EmailLogRepository;
import com.example.emailSender.repo.EmailScheduleRepository;
import com.example.emailSender.repo.EmailTemplateRepository;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailScheduleRepository emailScheduleRepository;

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public EmailTemplate createTemplate(EmailTemplate template) {
        return emailTemplateRepository.save(template);
    }

    @Override
    public EmailTemplate getTemplate(UUID id) {
        return emailTemplateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Template not found"));
    }

    @Override
    public List<EmailTemplate> getAllTemplates() {
        return emailTemplateRepository.findAll();
    }

    @Override
    public EmailTemplate updateTemplate(UUID id, EmailTemplate template) {
        EmailTemplate existingTemplate = getTemplate(id);
        existingTemplate.setSubject(template.getSubject());
        existingTemplate.setBody(template.getBody());
        existingTemplate.setFrequency(template.getFrequency());
        existingTemplate.setActive(template.isActive());

        return emailTemplateRepository.save(existingTemplate);
    }

    @Override
    public void deleteTemplate(UUID id) {
        emailTemplateRepository.deleteById(id);
    }

    @Override
    public void sendScheduledEmails() {
        LocalDateTime now = LocalDateTime.now();
        logger.info("Checking for scheduled emails to send at or before {}", now);
        List<EmailSchedule> schedules = emailScheduleRepository.findByScheduledTimeBeforeOrEquals(now);

        for (EmailSchedule schedule : schedules) {
            EmailTemplate template = emailTemplateRepository.findById(schedule.getTemplate().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Template not found"));
            if (template.isActive()) {
                String recipientEmail = "darko_markovski@hotmail.com"; // Replace with actual recipient logic
                logger.info("Sending email to {}", recipientEmail);
                boolean sent = sendEmail(template, recipientEmail);
                logEmail(template, sent);
                if (sent) {
                    updateNextScheduledDate(schedule, template);
                }
            } else {
                logger.info("Template is not active, skipping email");
            }
        }
    }

    @Override
    public List<EmailSchedule> getSchedulesByScheduledDate(LocalDateTime now) {
        logger.info("Fetching schedules for time {}", now);
        return emailScheduleRepository.findByScheduledTime(now);
    }

    @Override
    public void updateNextScheduledDate(EmailSchedule schedule, EmailTemplate template) {
        LocalDateTime nextScheduledTime = calculateNextScheduledDate(template.getFrequency(), schedule.getScheduledTime());
        schedule.setScheduledTime(nextScheduledTime);
        emailScheduleRepository.save(schedule);
        logger.info("Updated next scheduled time to {}", nextScheduledTime);
    }

    @Override
    public boolean sendEmail(EmailTemplate template, String recipientEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject(template.getSubject());
            helper.setText(template.getBody(), true);

            mailSender.send(message);
            logger.info("Email sent to {}", recipientEmail);
            return true;
        } catch (jakarta.mail.MessagingException e) {
            logEmailError(template, e.getMessage());
            logger.error("Failed to send email to {}: {}", recipientEmail, e.getMessage());
            return false;
        }
    }


    private LocalDateTime calculateNextScheduledDate(String frequency, LocalDateTime currentScheduledDate) {
        switch (frequency) {
            case "daily":
                return currentScheduledDate.plusDays(1);
            case "weekly":
                return currentScheduledDate.plusWeeks(1);
            case "monthly":
                return currentScheduledDate.plusMonths(1);
            default:
                throw new IllegalArgumentException("Unknown frequency: " + frequency);
        }
    }

    @Override
    public void logEmail(EmailTemplate template, boolean success) {
        EmailLog log = new EmailLog();
        log.setId(UUID.randomUUID());
        log.setEmailTemplate(template);
        log.setSentTime(LocalDateTime.now());
        log.setStatus(success ? "success" : "failure");
        emailLogRepository.save(log);
        logger.info("Logged email status: {}", success ? "success" : "failure");
    }

    private void logEmailError(EmailTemplate template, String errorMessage) {
        EmailLog log = new EmailLog();
        log.setId(UUID.randomUUID());
        log.setEmailTemplate(template);
        log.setSentTime(LocalDateTime.now());
        log.setStatus("failure");
        log.setErrorMessage(errorMessage);
        emailLogRepository.save(log);
        logger.error("Logged email error: {}", errorMessage);
    }
    private void createInitialEmailSchedule(EmailTemplate template) {
        EmailSchedule schedule = new EmailSchedule();
        schedule.setTemplate(template);
        LocalDateTime nextScheduledTime = calculateNextScheduledDate(template.getFrequency(), LocalDateTime.now());
        schedule.setScheduledTime(nextScheduledTime);
        emailScheduleRepository.save(schedule);
        logger.info("Created initial email schedule for template with ID {}: {}", template.getId(), nextScheduledTime);
    }

    @Override
    public void sendAllEmailsOnce() {
        List<EmailTemplate> templates = emailTemplateRepository.findAll();
        for (EmailTemplate template : templates) {
            if (template.isActive()) {
                String recipientEmail = "darko_markovski@hotmail.com"; // Replace with actual recipient logic
                boolean sent = sendEmail(template, recipientEmail);
                if (sent) {
                    createInitialEmailSchedule(template);
                }
                logEmail(template, sent);
            }
        }
    }
}
