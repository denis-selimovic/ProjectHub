package ba.unsa.etf.nwt.notificationservice.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ResourceOwner {

    private UUID id;
    private String email;
    private String clientId;
    private String accessToken;
    private ArrayList<String> scopes;

    public ResourceOwner(OAuth2AuthenticationDetails details) {
        Map<String, ?> decoded = (Map<String, ?>) details.getDecodedDetails();
        id = UUID.fromString((String) decoded.get("id"));
        email = (String) decoded.get("email");
        clientId = (String) decoded.get("client_id");
        accessToken = details.getTokenValue();
        scopes = (ArrayList<String>) decoded.get("scope");
    }

    public String getAuthHeader() {
        return "Bearer " + accessToken;
    }
}
