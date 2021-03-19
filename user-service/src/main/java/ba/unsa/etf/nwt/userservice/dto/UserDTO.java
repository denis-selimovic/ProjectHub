package ba.unsa.etf.nwt.userservice.dto;

import ba.unsa.etf.nwt.userservice.model.User;
import ba.unsa.etf.nwt.userservice.response.interfaces.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Resource {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private Instant createdAt;
    private Instant updatedAt;

    public UserDTO(User user) {
        id = user.getId();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        createdAt = user.getCreatedAt();
        updatedAt = user.getUpdatedAt();
    }
}
