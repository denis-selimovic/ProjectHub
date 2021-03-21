package ba.unsa.etf.nwt.taskservice.service;

import ba.unsa.etf.nwt.taskservice.dto.IssueDTO;
import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.exception.base.UnpocessableEntityException;
import ba.unsa.etf.nwt.taskservice.filter.GenericSpecificationBuilder;
import ba.unsa.etf.nwt.taskservice.filter.SearchCriteria;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import ba.unsa.etf.nwt.taskservice.model.Priority;
import ba.unsa.etf.nwt.taskservice.repository.IssueRepository;
import ba.unsa.etf.nwt.taskservice.request.CreateIssueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final GenericSpecificationBuilder<Issue> builder;

    private final IssueRepository issueRepository;
    private final PriorityService priorityService;

    public Issue create(final CreateIssueRequest request) {
        issueRepository.findByNameAndProjectId(request.getName(), request.getProjectId()).ifPresent(i -> {
            throw new UnpocessableEntityException("Issue with this name already exists");
        });
        Issue issue = createIssueFromRequest(request);
        return issueRepository.save(issue);
    }

    public Page<IssueDTO> filter(Pageable pageable, UUID projectId, String priorityId) {
        Root<Issue> root = builder.setup(Issue.class);
        Specification<Issue> specification = builder
                .with(root.get("projectId"), projectId.toString(), SearchCriteria.SearchCriteriaOperation.EQ)
                .with(root.get("priority").get("id"), priorityId, SearchCriteria.SearchCriteriaOperation.EQ)
                .build();
        return issueRepository.findAll(specification, pageable);
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
            throw new NotFoundException("Issue not found");
        });
    }
}
