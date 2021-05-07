package ba.unsa.etf.nwt.emailservice.emailservice.messaging.consumers;

import ba.unsa.etf.nwt.emailservice.emailservice.dto.UserDTO;
import ba.unsa.etf.nwt.emailservice.emailservice.exception.base.UnprocessableEntityException;
import ba.unsa.etf.nwt.emailservice.emailservice.messaging.Consumer;
import ba.unsa.etf.nwt.emailservice.emailservice.messaging.publishers.UserPublisher;
import ba.unsa.etf.nwt.emailservice.emailservice.service.EmailConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer implements Consumer<UserDTO> {

    private final EmailConfigService emailConfigService;
    private final UserPublisher userPublisher;

    @RabbitListener(queues = "user-service")
    public void receive(UserDTO data) {
        try {
            emailConfigService.create(data);
        } catch (UnprocessableEntityException e) {
            userPublisher.send(data);
        }
    }
}
