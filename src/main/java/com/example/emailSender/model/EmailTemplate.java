package com.example.emailSender.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "RAW")
    private UUID id;

    @Column(name = "BODY", nullable = true, length = 255)
    private String body;

    @Column(name = "FREQUENCY", nullable = true, length = 255)
    private String frequency;

    @Column(name = "SUBJECT", nullable = true, length = 255)
    private String subject;

    @Column(name = "CREATED_AT", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "ACTIVE", nullable = true)
    private boolean active;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
