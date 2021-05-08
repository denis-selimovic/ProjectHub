package ba.unsa.etf.nwt.taskservice.messaging.publishers;

import ba.unsa.etf.nwt.taskservice.dto.TaskNotificationDTO;
import ba.unsa.etf.nwt.taskservice.messaging.Publisher;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.model.Status;
import ba.unsa.etf.nwt.taskservice.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TaskNotificationPublisher implements Publisher<TaskNotificationDTO> {

    private final RabbitTemplate template;
    private final DirectExchange exchange;

    @Value("${amqp.routing-key}")
    private String routingKey;

    private final Set<String> properties =
            new HashSet<>(Arrays.asList("description", "name", "priority", "status", "type", "updatedBy", "userId"));

    @Override
    public void send(TaskNotificationDTO data) {
        this.template.convertAndSend(exchange.getName(), routingKey, data);
    }

    public TaskNotificationDTO createNotification(Object[] previous, Object[] current, String[] properties) {
        TaskNotificationDTO data = new TaskNotificationDTO();
        for (int i = 0; i < properties.length; ++i) {
            if (!this.properties.contains(properties[i])) continue;
            if (current[i] == null || previous[i] == null) continue;

            if (properties[i].equals("updatedBy"))
                data.setUpdatedBy(current[i].toString());
            else if (current[i].getClass().equals(String.class) || current[i].getClass().equals(UUID.class))
                data.addChange(properties[i], previous[i].toString(), current[i].toString());
            else if (current[i] instanceof Priority) {
                Priority p = (Priority) previous[i];
                Priority c = (Priority) current[i];
                data.addChange(properties[i], p.getPriorityType().toString(), c.getPriorityType().toString());
            }
            else if (current[i] instanceof Status) {
                Status p = (Status) previous[i];
                Status c = (Status) current[i];
                data.addChange(properties[i], p.getStatus().toString(), c.getStatus().toString());
            }
            else if (current[i] instanceof Type) {
                Type p = (Type) previous[i];
                Type c = (Type) current[i];
                data.addChange(properties[i], p.getType().toString(), c.getType().toString());
            }
        }

        return data;
    }
}
