package com.example.emailSender.service;


import com.example.emailSender.exception.ResourceNotFoundException;
import com.example.emailSender.model.EmailTemplate;
import com.example.emailSender.repo.EmailTemplateRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public EmailTemplate createTemplate(EmailTemplate template) {
        EmailTemplate savedTemplate = emailTemplateRepository.save(template);
        sendEmail(savedTemplate);
        return savedTemplate;
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
        return emailTemplateRepository.save(existingTemplate);
    }

    @Override
    public void deleteTemplate(UUID id) {
        emailTemplateRepository.deleteById(id);
    }

    @Override
    public void sendScheduledEmails() {
        // Simulate sending scheduled emails
        List<EmailTemplate> scheduledTemplates = emailTemplateRepository.findByFrequency("daily");
        for (EmailTemplate template : scheduledTemplates) {
            sendEmail(template);
        }
    }

    public void sendEmail(EmailTemplate template) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

           // helper.setFrom("drkmarkovski@gmail.com");
            helper.setTo("darko_markovski@hotmail.com"); // Replace with actual recipient
           // helper.setSubject("test");
            helper.setSubject(template.getSubject());
           helper.setText(template.getBody(), true); // true indicates the message is HTML
            //  helper.setText("TEST"); // true indicates the message is HTML
            mailSender.send(message);
            System.out.println("Email sent successfully with subject: " + template.getSubject());
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email with subject: " + template.getSubject());
        }
    }
}

