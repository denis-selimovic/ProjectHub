package ba.unsa.etf.nwt.taskservice.repository;

import ba.unsa.etf.nwt.taskservice.dto.IssueDTO;
import ba.unsa.etf.nwt.taskservice.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueRepository extends PagingAndSortingRepository<Issue, UUID> {
    Optional<Issue> findByNameAndProjectId(String name, UUID projectId);
    Page<IssueDTO> findAll(Specification<Issue> specification, Pageable pageable);

    @Query(value = "DELETE FROM tasks t WHERE t.project_id = :projectId", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteAllByProjectId(UUID projectId);
}
