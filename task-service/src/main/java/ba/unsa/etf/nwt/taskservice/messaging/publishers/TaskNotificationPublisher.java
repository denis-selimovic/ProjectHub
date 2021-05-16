package ba.unsa.etf.nwt.taskservice.messaging.publishers;

import ba.unsa.etf.nwt.taskservice.config.RabbitMQConfig;
import ba.unsa.etf.nwt.taskservice.dto.TaskNotificationDTO;
import ba.unsa.etf.nwt.taskservice.messaging.Publisher;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.model.Status;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskNotificationPublisher implements Publisher<TaskNotificationDTO> {

    private final RabbitTemplate template;

    private final Set<String> properties =
            new HashSet<>(Arrays.asList("description", "name", "priority", "status", "type", "updatedBy", "userId"));

    @Override
    public void send(TaskNotificationDTO data) {
        this.template.convertAndSend(
                RabbitMQConfig.TaskNotificationQueueConfig.EXCHANGE_NAME,
                RabbitMQConfig.TaskNotificationQueueConfig.ROUTING_KEY,
                data
        );
    }

    public TaskNotificationDTO createNotification(Object[] previous, Object[] current, String[] properties, Task task) {
        TaskNotificationDTO data = new TaskNotificationDTO();
        data.setTaskId(task.getId());
        data.setTaskName(task.getName());
        for (int i = 0; i < properties.length; ++i) {
            if (!this.properties.contains(properties[i])) continue;

            if (properties[i].equals("updatedBy"))
                data.setUpdatedBy(current[i].toString());
            else if (properties[i].equals("userId")) {
                if (previous[i] != null && current[i] != null && !previous[i].toString().equals(current[i].toString()))
                    data.addChange("userId", previous[i].toString(), current[i].toString());
                else if (previous[i] != null)
                    data.addChange("userId", previous[i].toString(), null);
                else if (current[i] != null)
                    data.addChange("userId", null, current[i].toString());
            }
            else if (current[i].getClass().equals(String.class) || current[i].getClass().equals(UUID.class)) {
                String p = previous[i].toString();
                String c = current[i].toString();
                if (!p.equals(c)) data.addChange(properties[i], p, c);
            }
            else if (current[i] instanceof Priority) {
                String p = ((Priority) previous[i]).getPriorityType().toString();
                String c = ((Priority) current[i]).getPriorityType().toString();
                if (!p.equals(c)) data.addChange(properties[i], p, c);
            }
            else if (current[i] instanceof Status) {
                String p = ((Status) previous[i]).getStatus().toString();
                String c = ((Status) current[i]).getStatus().toString();
                if (!p.equals(c)) data.addChange(properties[i], p, c);
            }
            else if (current[i] instanceof Type) {
                String p = ((Type) previous[i]).getType().toString();
                String c = ((Type) current[i]).getType().toString();
                if (!p.equals(c)) data.addChange(properties[i], p, c);
            }
        }

        return data;
    }

    public TaskNotificationDTO createNotification(Task task) {
        TaskNotificationDTO data = new TaskNotificationDTO(task.getUpdatedBy().toString(), task.getId(), task.getName());
        String userId = (task.getUserId() != null) ? task.getUserId().toString() : null;
        data.addChange("userId", null, userId);
        return data;
    }
}
