package ba.unsa.etf.nwt.emailservice.emailservice.service;

import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.BadRequestException;
import ba.unsa.etf.nwt.emailservice.emailservice.request.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Profile("dev")
public class EmailService {

    @Value("${mail.from}")
    private String from;

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    public void sendEmail(SendEmailRequest request) {
        boolean activation = request.getType().equals("activation");
        String subject = activation ? "Activate your accounts" : "Reset your password";
        String template = activation ? "emails/activate" : "emails/reset";
        try {
            sendEmail(subject, request.getEmail(), template, Map.of(
                    "name", request.getFirstName(),
                    "token", request.getToken()
            ));
        } catch (MessagingException e) {
            throw new BadRequestException("Bad request");
        }
    }

    private void sendEmail(String subject, String to, String template, Map<String, String> params)
            throws MessagingException {
        String process = loadTemplate(template, params);
        MimeMessage message = createMessage(subject, to, process);
        Executors.newCachedThreadPool().submit(() -> javaMailSender.send(message));
    }

    private MimeMessage createMessage(String subject, String to, String process) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(process, true);
        return message;
    }

    private String loadTemplate(String path, Map<String, String> params) {
        Context context = new Context();
        params.forEach(context::setVariable);
        return templateEngine.process(path, context);
    }
}
