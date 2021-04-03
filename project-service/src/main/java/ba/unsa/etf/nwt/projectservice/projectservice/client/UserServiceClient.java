package ba.unsa.etf.nwt.projectservice.projectservice.client;


import ba.unsa.etf.nwt.projectservice.projectservice.client.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(value = "user-service")
@Component
public interface UserServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/users/{userId}")
    ResponseEntity<User> getUserById(@RequestHeader("Authorization") String bearerToken,
                                     @PathVariable UUID userId);
}
