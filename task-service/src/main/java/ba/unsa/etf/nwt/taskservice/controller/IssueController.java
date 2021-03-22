package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.dto.IssueDTO;
import ba.unsa.etf.nwt.taskservice.dto.MetadataDTO;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.request.CreateIssueRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.PaginatedResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommunicationService;
import ba.unsa.etf.nwt.taskservice.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final CommunicationService communicationService;

    @PostMapping
    public ResponseEntity<Response> create(ResourceOwner resourceOwner, @RequestBody @Valid CreateIssueRequest request) {
        communicationService.checkIfProjectExists(request.getProjectId());
        communicationService.checkIfCollaborator(resourceOwner.getId(), request.getProjectId());
        Issue issue = issueService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(new IssueDTO(issue)));
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<Response> delete(ResourceOwner resourceOwner, @PathVariable UUID issueId) {
        Issue issue = issueService.findById(issueId);
        communicationService.checkIfOwner(resourceOwner.getId(), issue.getProjectId());
        issueService.delete(issue);
        return ResponseEntity.status(HttpStatus.OK).body(new Response(new SimpleResponse("Issue successfully deleted")));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse> getIssues(ResourceOwner resourceOwner,
                                                       Pageable pageable,
                                                       @RequestParam(name = "project_id") UUID projectId,
                                                       @RequestParam(required = false, name = "priority_id") String priorityId) {
        communicationService.checkIfCollaborator(resourceOwner.getId(), projectId);
        Page<IssueDTO> issuePage = issueService.filter(pageable, projectId, priorityId);
        return ResponseEntity.ok(new PaginatedResponse(new MetadataDTO(issuePage), issuePage.getContent()));
    }
}
