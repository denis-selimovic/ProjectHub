package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.client.dto.UserDTO;
import ba.unsa.etf.nwt.taskservice.client.service.ProjectService;
import ba.unsa.etf.nwt.taskservice.client.service.UserService;
import ba.unsa.etf.nwt.taskservice.dto.CommentDTO;
import ba.unsa.etf.nwt.taskservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.taskservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.taskservice.model.Comment;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.request.create.CreateCommentRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.taskservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommentService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden: User not owner or collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<CommentDTO>> create(ResourceOwner resourceOwner,
                                           @PathVariable UUID taskId,
                                           @RequestBody @Valid CreateCommentRequest request) {
        Task task = taskService.findById(taskId);
        projectService.findProjectById(resourceOwner, task.getProjectId());
        Comment comment = commentService.create(request, task, resourceOwner.getId());

        projectService.findCollaboratorById(resourceOwner, resourceOwner.getId(), task.getProjectId());
        UserDTO userDTO = userService.getUserById(resourceOwner, resourceOwner.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new CommentDTO(comment, userDTO)));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Forbidden: User not owner or collaborator on project", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found: Task not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<CommentDTO, MetadataDTO>> getCommentsForTask(ResourceOwner resourceOwner,
                                                                @PathVariable UUID taskId,
                                                                Pageable pageable) {
        Task task = taskService.findById(taskId);
        projectService.findProjectById(resourceOwner, task.getProjectId());
        Page<CommentDTO> commentPage = commentService.getCommentsForTask(task, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PaginatedResponse<>(new MetadataDTO(commentPage), commentPage.getContent()));
    }

    @DeleteMapping("/{commentId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment deleted"),
            @ApiResponse(code = 403, message = "Forbidden: Only the author can delete the comment", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found: Comment not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> delete(ResourceOwner resourceOwner,
                                           @PathVariable UUID taskId,
                                           @PathVariable UUID commentId) {
        Comment comment = commentService.findByIdAndTaskId(commentId, taskId);
        if(!comment.getUserId().equals(resourceOwner.getId())) {
            throw new ForbiddenException("You don't have permission for this action");
        }
        commentService.delete(comment);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Comment successfully deleted")));
    }
}
