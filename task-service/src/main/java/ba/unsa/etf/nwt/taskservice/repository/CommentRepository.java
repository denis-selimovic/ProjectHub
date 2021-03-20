package ba.unsa.etf.nwt.taskservice.repository;

import ba.unsa.etf.nwt.taskservice.model.Comment;
import ba.unsa.etf.nwt.taskservice.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, UUID> {
    Page<Comment> findAllByTask(Task task, Pageable pageable);
}
