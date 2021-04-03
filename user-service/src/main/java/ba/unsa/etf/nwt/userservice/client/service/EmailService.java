package ba.unsa.etf.nwt.userservice.client.service;

import ba.unsa.etf.nwt.userservice.client.EmailServiceClient;
import ba.unsa.etf.nwt.userservice.client.dto.EmailDTO;
import ba.unsa.etf.nwt.userservice.client.request.SendEmailRequest;
import ba.unsa.etf.nwt.userservice.model.Token;
import ba.unsa.etf.nwt.userservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailServiceClient emailServiceClient;

    public EmailDTO sendEmail(User user, Token token, String type) {
        SendEmailRequest request = new SendEmailRequest(type, user.getEmail(),
                user.getFirstName(), token.getToken());
        return emailServiceClient.sendEmail(request).getBody();
    }
}
