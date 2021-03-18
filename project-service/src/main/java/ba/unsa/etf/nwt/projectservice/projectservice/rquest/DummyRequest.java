package ba.unsa.etf.nwt.projectservice.projectservice.rquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DummyRequest {
    @Size(min = 2, message = "Min size is 2")
    private String name;

    @NotBlank(message = "Surname can't be blank")
    private String surname;
}
