package ba.unsa.etf.nwt.userservice.response.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@Data
@EqualsAndHashCode(callSuper = false)
public class GrantTokenResponse{

    @JsonProperty("token_type")
    private final String tokenType;

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    @JsonProperty("scope")
    private final String scope;

    @JsonProperty("expires_in")
    private final Integer expiresIn;

    public GrantTokenResponse(OAuth2AccessToken oauthResponse) {
        tokenType = oauthResponse.getTokenType();
        accessToken = oauthResponse.getValue();
        refreshToken = oauthResponse.getRefreshToken().getValue();
        scope = String.join(" ", oauthResponse.getScope());
        expiresIn = oauthResponse.getExpiresIn();
    }
}
