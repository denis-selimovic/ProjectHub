package ba.unsa.etf.nwt.emailservice.emailservice.client.service;

import ba.unsa.etf.nwt.emailservice.emailservice.client.TaskServiceClient;
import ba.unsa.etf.nwt.emailservice.emailservice.client.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskServiceClient taskServiceClient;

    public TaskDTO findTaskById(String authHeader, UUID taskId) {
        return taskServiceClient.getTaskById(authHeader, taskId)
                .getBody()
                .getData();
    }
}
