package com.example.emailSender.repo;

import com.example.emailSender.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, UUID> {
    // Additional query methods (if needed) can be defined here
    List<EmailTemplate> findAll();
}
