package ba.unsa.etf.nwt.taskservice.messaging.publishers;

import ba.unsa.etf.nwt.taskservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.taskservice.dto.TaskNotificationDTO;
import ba.unsa.etf.nwt.taskservice.messaging.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskNotificationEmailPublisher implements Publisher<TaskNotificationDTO> {

    private final RabbitTemplate template;

    @Override
    public void send(TaskNotificationDTO data) {
        this.template.convertAndSend(
                RabbitMQConfig.TaskNotificationEmailQueueConfig.EXCHANGE_NAME,
                RabbitMQConfig.TaskNotificationEmailQueueConfig.ROUTING_KEY,
                data
        );
    }
}
