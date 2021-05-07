package ba.unsa.etf.nwt.userservice.messaging.consumers;

import ba.unsa.etf.nwt.userservice.dto.UserDTO;
import ba.unsa.etf.nwt.userservice.messaging.Consumer;
import ba.unsa.etf.nwt.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer implements Consumer<UserDTO> {

    private final UserService userService;

    @RabbitListener(queues = {"email-service", "notification-service"})
    public void receive(UserDTO data) {
        userService.delete(data.getId());
    }
}
