package ba.unsa.etf.nwt.taskservice.client;

import ba.unsa.etf.nwt.taskservice.client.dto.ProjectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(value = "project-service")
@Component
public interface ProjectServiceClient {
    @RequestMapping(method = RequestMethod.GET,value = "/api/v1/projects/{projectId}")
    ResponseEntity<ProjectDTO> getProjectById(@RequestHeader("Authorization") String bearerToken,
                                              @PathVariable UUID projectId);
}
