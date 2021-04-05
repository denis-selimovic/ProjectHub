package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.client.service.ProjectService;
import ba.unsa.etf.nwt.taskservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.taskservice.dto.TaskDTO;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.request.create.CreateTaskRequest;
import ba.unsa.etf.nwt.taskservice.request.patch.PatchTaskRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.taskservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.TaskService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Task created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden: User not owner or collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<TaskDTO>> create(ResourceOwner resourceOwner, @RequestBody @Valid CreateTaskRequest request) {
        projectService.findProjectById(resourceOwner, request.getProjectId());
        if (request.getUserId() != null) {
            projectService.findCollaboratorById(resourceOwner, request.getUserId(), request.getProjectId());
        }
        Task task = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new TaskDTO(task)));
    }

    @DeleteMapping("/{taskId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task deleted"),
            @ApiResponse(code = 403, message = "Forbidden: Only the project owner can delete tasks", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found: Task not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> delete(ResourceOwner resourceOwner, @PathVariable UUID taskId) {
        Task task = taskService.findById(taskId);
        projectService.findProjectByIdAndOwner(resourceOwner, task.getProjectId());
        taskService.delete(task);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Task successfully deleted")));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden: User not owner or collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<TaskDTO, MetadataDTO>> getTasks(ResourceOwner resourceOwner,
                                                                            Pageable pageable,
                                                                            @RequestParam(name = "project_id") UUID projectId,
                                                                            @RequestParam(required = false, name = "priority_id") String priorityId,
                                                                            @RequestParam(required = false, name = "status_id") String statusId,
                                                                            @RequestParam(required = false, name = "type_id") String typeId) {
        projectService.findProjectById(resourceOwner, projectId);
        Page<TaskDTO> taskPage = taskService.filter(pageable, projectId, priorityId, statusId, typeId);
        return ResponseEntity.ok(new PaginatedResponse<>(new MetadataDTO(taskPage), taskPage.getContent()));
    }

    @PatchMapping("/{taskId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task updated"),
            @ApiResponse(code = 404, message = "Task not found"),
            @ApiResponse(code = 403, message = "Forbidden: User not owner or collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<TaskDTO>> patch(ResourceOwner resourceOwner,
                                                   @PathVariable UUID taskId,
                                                   @RequestBody @Valid PatchTaskRequest patchTaskRequest) {
        Task task = taskService.findById(taskId);
        projectService.findProjectById(resourceOwner, task.getProjectId());
        taskService.patch(task, patchTaskRequest);
        return ResponseEntity.ok().body(new Response<>(new TaskDTO(task)));
    }
}
