package com.example.emailSender.service;


import com.example.emailSender.model.EmailTemplate;

import java.util.List;
import java.util.UUID;

public interface EmailService {
    EmailTemplate createTemplate(EmailTemplate template);
    void sendEmail(EmailTemplate template);
    EmailTemplate getTemplate(UUID id);
    List<EmailTemplate> getAllTemplates();
    EmailTemplate updateTemplate(UUID id, EmailTemplate template);
    void deleteTemplate(UUID id);
    void sendScheduledEmails();
}
