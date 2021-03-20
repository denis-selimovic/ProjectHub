package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.model.Comment;
import ba.unsa.etf.nwt.taskservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Resource {
    private UUID id;
    private String text;
    private UUID userId;
    private UUID taskId;
    private Instant createdAt;
    private Instant updatedAt;

    public CommentDTO(final Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.userId = comment.getUserId();
        this.taskId = comment.getTask().getId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
