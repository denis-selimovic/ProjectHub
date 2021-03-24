package ba.unsa.etf.nwt.projectservice.projectservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchProjectRequest {

    @NotBlank(message = "Project name can't be blank")
    @Size(max = 50, message = "Project name can contain at most 50 characters")
    private JsonNullable<String> name = JsonNullable.undefined();

    @NotNull(message = "Project must have an owner")
    @JsonProperty("owner_id")
    private JsonNullable<UUID> ownerId = JsonNullable.undefined();

}
