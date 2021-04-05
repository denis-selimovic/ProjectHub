package ba.unsa.etf.nwt.notificationservice;

import ba.unsa.etf.nwt.notificationservice.security.ResourceOwner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/test")
@EnableFeignClients
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @GetMapping
    public ResponseEntity<?> hello(ResourceOwner resourceOwner) {
        return ResponseEntity.ok(resourceOwner);
    }
}
