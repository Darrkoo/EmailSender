package com.example.emailSender.repo;

import com.example.emailSender.model.EmailSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EmailScheduleRepository extends JpaRepository<EmailSchedule, UUID> {
    // Custom query method to find schedules by scheduled time
    List<EmailSchedule> findByScheduledTime(LocalDateTime scheduledTime);
    // Custom query method to find schedules by scheduled time
    @Query("SELECT es FROM EmailSchedule es WHERE es.scheduledTime <= :time")
    List<EmailSchedule> findByScheduledTimeBeforeOrEquals(@Param("time") LocalDateTime time);
}
