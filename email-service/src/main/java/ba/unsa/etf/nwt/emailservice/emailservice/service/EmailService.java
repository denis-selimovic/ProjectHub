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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${mail.from}")
    private String from;

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    public void sendEmail(String subject, String to, String template, Map<String, String> params)
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
