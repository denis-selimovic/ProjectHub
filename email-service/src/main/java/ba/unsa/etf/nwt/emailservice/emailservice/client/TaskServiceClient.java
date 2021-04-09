package ba.unsa.etf.nwt.emailservice.emailservice.client;

import ba.unsa.etf.nwt.emailservice.emailservice.client.dto.TaskDTO;
import ba.unsa.etf.nwt.emailservice.emailservice.response.base.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(value = "task-service")
@Component
public interface TaskServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/tasks/{taskId}")
    ResponseEntity<Response<TaskDTO>> getTaskById(@RequestHeader("Authorization") String bearerToken,
                                                  @PathVariable UUID taskId);
}
