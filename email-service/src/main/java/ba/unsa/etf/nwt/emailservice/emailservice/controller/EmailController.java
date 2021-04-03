package ba.unsa.etf.nwt.emailservice.emailservice.controller;

import ba.unsa.etf.nwt.emailservice.emailservice.request.SendEmailRequest;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public EmailDTO sendEmail(@RequestBody @Valid SendEmailRequest request) {
        try {
            emailService.sendEmail(request);
        } catch (MessagingException e) {
            return null;
        }
        return null;
    }
}
