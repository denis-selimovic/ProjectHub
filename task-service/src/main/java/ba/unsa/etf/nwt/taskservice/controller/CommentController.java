package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.dto.CommentDTO;
import ba.unsa.etf.nwt.taskservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.taskservice.model.Comment;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.request.CreateCommentRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommentService;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommunicationService communicationService;
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Response> create(ResourceOwner resourceOwner, @RequestBody @Valid CreateCommentRequest request) {
        Task task = taskService.findById(request.getTaskId());
        communicationService.checkIfCollaborator(resourceOwner.getId(), task.getProjectId());
        Comment comment = commentService.create(request, task, resourceOwner.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(new CommentDTO(comment)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Response> delete(ResourceOwner resourceOwner, @PathVariable UUID commentId) {
        Comment comment = commentService.findById(commentId);
        if(!comment.getUserId().equals(resourceOwner.getId())) {
            throw new ForbiddenException("You don't have permission for this action");
        }
        commentService.delete(comment);
        return ResponseEntity.status(HttpStatus.OK).body(new Response(new SimpleResponse("Comment successfully deleted")));
    }
}
