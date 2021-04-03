package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.client.service.ProjectService;
import ba.unsa.etf.nwt.taskservice.dto.IssueDTO;
import ba.unsa.etf.nwt.taskservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.request.create.CreateIssueRequest;
import ba.unsa.etf.nwt.taskservice.request.patch.PatchIssueRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.ErrorResponse;
import ba.unsa.etf.nwt.taskservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommunicationService;
import ba.unsa.etf.nwt.taskservice.service.IssueService;
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
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final CommunicationService communicationService;
    private final ProjectService projectService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Issue created"),
            @ApiResponse(code = 422, message = "Unprocessable entity: Validation fail", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden: User not collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Response<IssueDTO>> create(ResourceOwner resourceOwner, @RequestBody @Valid CreateIssueRequest request) {
        projectService.findProjectById(resourceOwner, request.getProjectId());
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

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 403, message = "Forbidden: User not collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PaginatedResponse<IssueDTO, MetadataDTO>> getIssues(ResourceOwner resourceOwner,
                                                       Pageable pageable,
                                                       @RequestParam(name = "project_id") UUID projectId,
                                                       @RequestParam(required = false, name = "priority_id") String priorityId) {
        communicationService.checkIfCollaborator(resourceOwner.getId(), projectId);
        Page<IssueDTO> issuePage = issueService.filter(pageable, projectId, priorityId);
        return ResponseEntity.ok(new PaginatedResponse<>(new MetadataDTO(issuePage), issuePage.getContent()));
    }

    @PatchMapping("/{issueId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Issue updated"),
            @ApiResponse(code = 404, message = "Issue not found"),
            @ApiResponse(code = 403, message = "Forbidden: User not collaborator on project", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Response<IssueDTO>> patch(ResourceOwner resourceOwner,
                                                   @PathVariable UUID issueId,
                                                   @RequestBody @Valid PatchIssueRequest patchIssueRequest) {
        Issue issue = issueService.findById(issueId);
        communicationService.checkIfCollaborator(resourceOwner.getId(), issue.getProjectId());
        issueService.patch(issue, patchIssueRequest);
        return ResponseEntity.ok().body(new Response<>(new IssueDTO(issue)));
    }
}
