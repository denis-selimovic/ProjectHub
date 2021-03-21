package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.dto.IssueDTO;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.request.CreateIssueRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommunicationService;
import ba.unsa.etf.nwt.taskservice.service.IssueService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final CommunicationService communicationService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Issue created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden: User not collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<IssueDTO>> create(ResourceOwner resourceOwner, @RequestBody @Valid CreateIssueRequest request) {
        communicationService.checkIfProjectExists(request.getProjectId());
        communicationService.checkIfCollaborator(resourceOwner.getId(), request.getProjectId());
        Issue issue = issueService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(new IssueDTO(issue)));
    }

    @DeleteMapping("/{issueId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Issue deleted"),
            @ApiResponse(code = 403, message = "Forbidden: Only the project owner can delete issues", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not found: Issue not found", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<SimpleResponse>> delete(ResourceOwner resourceOwner, @PathVariable UUID issueId) {
        Issue issue = issueService.findById(issueId);
        communicationService.checkIfOwner(resourceOwner.getId(), issue.getProjectId());
        issueService.delete(issue);
        return ResponseEntity.status(HttpStatus.OK).body(new Response<>(new SimpleResponse("Issue successfully deleted")));
    }
}
