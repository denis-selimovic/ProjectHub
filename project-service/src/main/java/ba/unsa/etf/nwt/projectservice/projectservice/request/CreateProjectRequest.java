package ba.unsa.etf.nwt.projectservice.projectservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class CreateProjectRequest {

    @NotBlank(message = "Project name can't be blank")
    @Size(max = 50, message = "Project name can contain at most 50 characters")
    @JsonProperty("name")
    private String name;

}
