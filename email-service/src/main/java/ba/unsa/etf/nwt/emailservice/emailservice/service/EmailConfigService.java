package ba.unsa.etf.nwt.emailservice.emailservice.service;

import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.emailservice.emailservice.model.EmailConfig;
import ba.unsa.etf.nwt.emailservice.emailservice.repository.EmailConfigRepository;
import ba.unsa.etf.nwt.emailservice.emailservice.request.CreateConfigRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailConfigService {

    private final EmailConfigRepository emailConfigRepository;

    public EmailConfig create(CreateConfigRequest request) {
        emailConfigRepository.findByUserId(request.getUserId()).ifPresent(c -> {
            throw new UnprocessableEntityException("Request cannot be processed");
        });
        EmailConfig emailConfig = createFromRequest(request);
        return emailConfigRepository.save(emailConfig);
    }

    private EmailConfig createFromRequest(CreateConfigRequest request) {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setEmail(request.getEmail());
        emailConfig.setUserId(request.getUserId());
        return emailConfig;
    }
}
