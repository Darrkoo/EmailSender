package com.example.emailSender.scheduler;

import com.example.emailSender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private EmailService emailService;

    // This cron expression triggers the task every minute
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleDailyEmails() {
        emailService.sendScheduledEmails();
    }

    // Additional schedules for monthly and yearly can be added here
}
