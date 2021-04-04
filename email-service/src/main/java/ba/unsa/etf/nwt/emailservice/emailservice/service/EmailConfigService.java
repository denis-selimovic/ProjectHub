package ba.unsa.etf.nwt.emailservice.emailservice.service;

import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailConfigRepository;
import ba.unsa.etf.nwt.emailservice.emailservice.request.CreateConfigRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmailConfigService {

    private final EmailConfigRepository emailConfigRepository;

    public EmailConfig create(CreateConfigRequest request) {
        emailConfigRepository.findByUserId(request.getUserId()).ifPresent(c -> {
            throw new UnprocessableEntityException("Request cannot be processed");
        });
        EmailConfig emailConfig = request.createEmailConfig();
        return emailConfigRepository.save(emailConfig);
    }

    public EmailConfig findByUserId(UUID userId) {
        return emailConfigRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("Config not found"));
    }
}
