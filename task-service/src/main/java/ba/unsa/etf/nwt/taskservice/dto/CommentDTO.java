package ba.unsa.etf.nwt.taskservice.dto;

import ba.unsa.etf.nwt.taskservice.client.dto.UserDTO;
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
    private UserDTO user;
    private Instant createdAt;
    private Instant updatedAt;

    public CommentDTO(final Comment comment, final UserDTO user) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = user;
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
