package com.example.emailSender.service;

import com.example.emailSender.model.EmailSchedule;
import com.example.emailSender.model.EmailTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EmailService {

    // CRUD operations for EmailTemplate
    EmailTemplate createTemplate(EmailTemplate template);
    EmailTemplate getTemplate(UUID id);
    List<EmailTemplate> getAllTemplates();
    EmailTemplate updateTemplate(UUID id, EmailTemplate template);
    void deleteTemplate(UUID id);

    // Email scheduling operations
    void sendScheduledEmails();
    List<EmailSchedule> getSchedulesByScheduledDate(LocalDateTime now);
    void updateNextScheduledDate(EmailSchedule schedule, EmailTemplate template);

    // Email sending operations
    boolean sendEmail(EmailTemplate template, String recipientEmail);
    void logEmail(EmailTemplate template, boolean sent);

     void sendAllEmailsOnce();
}
