package com.example.emailSender;

import com.example.emailSender.service.EmailService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSenderApplication.class, args);
	}


	@Bean
	public ApplicationRunner sendEmailsOnStartup(EmailService emailService) {
		return args -> {
			emailService.sendAllEmailsOnce();
		};
	}
}
