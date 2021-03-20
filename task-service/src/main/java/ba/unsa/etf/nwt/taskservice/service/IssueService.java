package ba.unsa.etf.nwt.taskservice.service;

import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.repository.IssueRepository;
import ba.unsa.etf.nwt.taskservice.request.CreateIssueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final PriorityService priorityService;

    public Issue create(final CreateIssueRequest request) {
        Issue issue = createIssueFromRequest(request);
        return issueRepository.save(issue);
    }

    private Issue createIssueFromRequest(final CreateIssueRequest request) {
        Priority priority =  priorityService.findById(request.getPriorityId());
        Issue issue = new Issue();
        issue.setName(request.getName());
        issue.setDescription(request.getDescription());
        issue.setProjectId(request.getProjectId());
        issue.setPriority(priority);
        return issue;
    }

    public void delete(final Issue issue) {
        issueRepository.delete(issue);
    }

    public Issue findById(final UUID issueId) {
        return issueRepository.findById(issueId).orElseThrow(() -> {
            throw new NotFoundException("Response can't be processed");
        });
    }
}
