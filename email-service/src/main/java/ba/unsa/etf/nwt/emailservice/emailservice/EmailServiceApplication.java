package ba.unsa.etf.nwt.emailservice.emailservice;

import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@SpringBootApplication
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class EmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

	private final EmailService emailService;

	@GetMapping
	public ResponseEntity<?> hello() {
		try {
			emailService.sendEmail("TEST", "Denis", "dselimovic1@etf.unsa.ba");
		} catch (MessagingException e) {
			return ResponseEntity.ok("NOT OK");
		}
		return ResponseEntity.ok("OK");
	}
}

