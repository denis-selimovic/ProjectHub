package ba.unsa.etf.nwt.taskservice;

import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/test")
public class TaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }

    @GetMapping
    public ResponseEntity<?> hello(ResourceOwner resourceOwner) {
        return ResponseEntity.ok(resourceOwner);
    }
}
