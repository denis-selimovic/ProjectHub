package ba.unsa.etf.nwt.taskservice.controller;

import ba.unsa.etf.nwt.taskservice.dto.IssueDTO;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.request.CreateIssueRequest;
import ba.unsa.etf.nwt.taskservice.response.SimpleResponse;
import ba.unsa.etf.nwt.taskservice.response.base.Response;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import ba.unsa.etf.nwt.taskservice.service.CommunicationService;
import ba.unsa.etf.nwt.taskservice.service.IssueService;
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
}
