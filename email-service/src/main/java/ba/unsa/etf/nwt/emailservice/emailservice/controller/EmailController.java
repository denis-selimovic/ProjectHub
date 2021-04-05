package ba.unsa.etf.nwt.emailservice.emailservice.controller;

import ba.unsa.etf.nwt.emailservice.emailservice.dto.EmailDTO;
import ba.unsa.etf.nwt.emailservice.emailservice.request.SendEmailRequest;
import ba.unsa.etf.nwt.emailservice.emailservice.response.base.Response;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
@Profile("dev")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Response<EmailDTO>> sendEmail(@RequestBody @Valid SendEmailRequest request) {
        emailService.sendEmail(request);
        return ResponseEntity.ok(new Response<>(new EmailDTO("Mail successfully sent", request.getEmail())));
    }
}
