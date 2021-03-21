package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.dto.TaskDTO;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.request.CreateTaskRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommunicationService;
import ba.unsa.etf.nwt.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final CommunicationService communicationService;

    @PostMapping
    public ResponseEntity<Response<TaskDTO>> create(ResourceOwner resourceOwner, @RequestBody @Valid CreateTaskRequest request) {
        communicationService.checkIfProjectExists(request.getProjectId());
        communicationService.checkIfCollaborator(resourceOwner.getId(), request.getProjectId());
        if (request.getUserId() != null) {
            communicationService.checkIfCollaborator(request.getUserId(), request.getProjectId());
        }
        Task task = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new TaskDTO(task)));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Response<SimpleResponse>> delete(ResourceOwner resourceOwner, @PathVariable UUID taskId) {
        Task task = taskService.findById(taskId);
        communicationService.checkIfOwner(resourceOwner.getId(), task.getProjectId());
        taskService.delete(task);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Task successfully deleted")));
    }
}
