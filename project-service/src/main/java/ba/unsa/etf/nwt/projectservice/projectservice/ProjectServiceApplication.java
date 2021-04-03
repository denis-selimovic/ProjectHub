package ba.unsa.etf.nwt.projectservice.projectservice;

import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/test")
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}

	@GetMapping
	public ResponseEntity<?> hello(ResourceOwner resourceOwner) {
		return ResponseEntity.ok(resourceOwner);
	}
}
