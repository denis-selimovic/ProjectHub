package ba.unsa.etf.nwt.emailservice.emailservice;

import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;

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
	public ResponseEntity<?> hello(@RequestParam(name = "type") String type) {
		try {
			String subject = (type.equals("reset")) ? "Reset your paassword" : "Activate your account";
			String template = (type.equals("reset")) ? "emails/reset" : "emails/activate";
			emailService.sendEmail(subject, "dselimovic1@etf.unsa.ba", template, Map.of("name", "Denis"));
		} catch (MessagingException e) {
			return ResponseEntity.ok("NOT OK");
		}
		return ResponseEntity.ok("OK");
	}
}

