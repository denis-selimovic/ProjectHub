package ba.unsa.etf.nwt.taskservice.request.patch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchIssueRequest {
    @NotBlank(message = "Issue name can't be blank")
    @Size(max = 50, message = "Issue name can contain at most 50 characters")
    @JsonProperty("name")
    private JsonNullable<String> name = JsonNullable.undefined();

    @NotBlank(message = "Issue description can't be blank")
    @Size(max = 255, message = "Issue description can contain at most 255 characters")
    @JsonProperty("description")
    private JsonNullable<String> description = JsonNullable.undefined();

    @NotNull(message = "Priority id can't be null")
    @JsonProperty("priority_id")
    private JsonNullable<UUID> priorityId = JsonNullable.undefined();
}
