package ba.unsa.etf.nwt.taskservice.request.create;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class CreateCommentRequest {
    @NotBlank(message = "Comment can't be blank")
    @Size(max = 255, message = "Comment can contain at most 255 characters")
    @JsonProperty("text")
    private String text;
}
