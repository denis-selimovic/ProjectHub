package ba.unsa.etf.nwt.taskservice.client.service;

import ba.unsa.etf.nwt.taskservice.client.UserServiceClient;
import ba.unsa.etf.nwt.taskservice.client.dto.UserDTO;
import ba.unsa.etf.nwt.taskservice.security.ResourceOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserServiceClient userServiceClient;

    public UserDTO getUserById(ResourceOwner resourceOwner, final UUID userId)  {
        return Objects.requireNonNull(userServiceClient
                .getUserById(resourceOwner.getAuthHeader(), userId)
                .getBody())
                .getData();
    }
}
