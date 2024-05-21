package com.example.emailSender.repo;


import com.example.emailSender.model.EmailSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailScheduleRepository extends JpaRepository<EmailSchedule, UUID> {
}

