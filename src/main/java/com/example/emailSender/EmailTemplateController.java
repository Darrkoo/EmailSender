package com.example.emailSender;


import com.example.emailSender.model.EmailTemplate;
import com.example.emailSender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/templates")
public class EmailTemplateController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<EmailTemplate> createTemplate(@RequestBody EmailTemplate template) {
        EmailTemplate createdTemplate = emailService.createTemplate(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTemplate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplate> getTemplate(@PathVariable UUID id) {
        return ResponseEntity.ok(emailService.getTemplate(id));
    }

    @GetMapping
    public ResponseEntity<List<EmailTemplate>> getAllTemplates() {
        return ResponseEntity.ok(emailService.getAllTemplates());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplate> updateTemplate(@PathVariable UUID id, @RequestBody EmailTemplate template) {
        return ResponseEntity.ok(emailService.updateTemplate(id, template));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable UUID id) {
        emailService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }
}
