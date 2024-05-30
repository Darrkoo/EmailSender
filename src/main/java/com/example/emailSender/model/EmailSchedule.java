package com.example.emailSender.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "EMAIL_SCHEDULE")
public class EmailSchedule {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "RAW")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID", nullable = false)
    private EmailTemplate emailTemplate;

    @Column(name = "SCHEDULED_TIME", nullable = true)
    private LocalDateTime scheduledTime;

    @Column(name = "STATUS", nullable = true, length = 255)
    private String status;

    @Column(name = "NEXT_SCHEDULED_TIME", nullable = true)
    private LocalDateTime nextScheduledTime;

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EmailTemplate getTemplate() {
        return emailTemplate;
    }

    public void setTemplate(EmailTemplate template) {
        this.emailTemplate = template;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getNextScheduledTime() {
        return nextScheduledTime;
    }

    public void setNextScheduledTime(LocalDateTime nextScheduledTime) {
        this.nextScheduledTime = nextScheduledTime;
    }
}
