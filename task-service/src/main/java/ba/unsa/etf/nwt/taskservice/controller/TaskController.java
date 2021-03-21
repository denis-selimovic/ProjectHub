package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.taskservice.dto.TaskDTO;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.request.CreateTaskRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommunicationService;
import ba.unsa.etf.nwt.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final CommunicationService communicationService;

    @PostMapping
    public ResponseEntity<Response> create(ResourceOwner resourceOwner, @RequestBody @Valid CreateTaskRequest request) {
        communicationService.checkIfProjectExists(request.getProjectId());
        communicationService.checkIfCollaborator(resourceOwner.getId(), request.getProjectId());
        if (request.getUserId() != null) {
            communicationService.checkIfCollaborator(request.getUserId(), request.getProjectId());
        }
        Task task = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(new TaskDTO(task)));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Response> delete(ResourceOwner resourceOwner, @PathVariable UUID taskId) {
        Task task = taskService.findById(taskId);
        communicationService.checkIfOwner(resourceOwner.getId(), task.getProjectId());
        taskService.delete(task);
        return ResponseEntity.status(HttpStatus.OK).body(new Response(new SimpleResponse("Task successfully deleted")));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse> getTasks(ResourceOwner resourceOwner,
                                                      Pageable pageable,
                                                      @RequestParam(name = "priority_id") UUID projectId,
                                                      @RequestParam(required = false, name = "priority_id") UUID priorityId,
                                                      @RequestParam(required = false, name = "status_id") UUID statusId,
                                                      @RequestParam(required = false, name = "type_id") UUID typeId) {
        communicationService.checkIfCollaborator(resourceOwner.getId(), projectId);
        Page<TaskDTO> taskPage = taskService.filter(pageable, projectId, priorityId, statusId, typeId);
        return ResponseEntity.ok(new PaginatedResponse(new MetadataDTO(taskPage), taskPage.getContent()));
    }
}
