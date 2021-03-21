package ba.unsa.etf.nwt.projectservice.projectservice.config.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@ActiveProfiles("test")
public class CustomTokenEnhancer implements TokenEnhancer {

    private UUID id;
    private String email;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(oAuth2AccessToken);
        Map<String, Object> oidc = Map.of(
                "id", id.toString(),
                "email", email
        );
        token.setAdditionalInformation(oidc);
        return token;
    }
}
