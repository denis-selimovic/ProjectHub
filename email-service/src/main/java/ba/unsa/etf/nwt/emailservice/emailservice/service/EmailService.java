package ba.unsa.etf.nwt.emailservice.emailservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${mail.from}")
    private String from;

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    public void sendEmail(String subject, String name, String to) throws MessagingException {
        String process = loadTemplate("emails/activate", name, UUID.randomUUID().toString());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(process, true);
        javaMailSender.send(message);
    }

    private String loadTemplate(String path, String name, String token) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("token", token);
        return templateEngine.process(path, context);
    }
}
