package ba.unsa.etf.nwt.taskservice.service;

import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.model.Comment;
import ba.unsa.etf.nwt.taskservice.model.Task;
import ba.unsa.etf.nwt.taskservice.repository.CommentRepository;
import ba.unsa.etf.nwt.taskservice.request.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(final CreateCommentRequest request, final Task task, final UUID authorId) {
        Comment comment = createCommentFromRequest(request, task, authorId);
        return commentRepository.save(comment);
    }

    private Comment createCommentFromRequest(final CreateCommentRequest request, final Task task, final UUID authorId) {
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setUserId(authorId);
        comment.setTask(task);
        return comment;
    }

    public Comment findById(final UUID commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Response can't be processed");
        });
    }

    public void delete(final Comment comment) {
        commentRepository.delete(comment);
    }

    public Page<Comment> getCommentsForTask(final Task task, final Pageable pageable) {
        return commentRepository.findAllByTask(task, pageable);
    }

    public Comment findByIdAndTaskId(final UUID commentId, final UUID taskId) {
        return commentRepository.findByIdAndTask_Id(commentId, taskId).orElseThrow(() -> {
            throw new NotFoundException("Response can't be processed");
        });
    }
}
