package com.example.emailSender.scheduler;

import com.example.emailSender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 60000) // Check every minute
    public void scheduleEmails() {
        emailService.sendScheduledEmails();
    }}
