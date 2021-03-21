package ba.unsa.etf.nwt.taskservice.repository;

import ba.unsa.etf.nwt.taskservice.dto.IssueDTO;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueRepository extends PagingAndSortingRepository<Issue, UUID>, JpaSpecificationExecutor<Issue> {
    Optional<Issue> findByNameAndProjectId(String name, UUID projectId);
    Page<IssueDTO> findAll(Pageable pageable, Specification<Issue> specification);
}
