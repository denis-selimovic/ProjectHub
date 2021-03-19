package ba.unsa.etf.nwt.userservice.security;

import ba.unsa.etf.nwt.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

@RequiredArgsConstructor
public class CustomTokenEnhancer implements TokenEnhancer {

    private final UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(oAuth2AccessToken);
        userService.findByEmail(userAuthentication.getName()).ifPresent(user -> {
            Map<String, Object> oidc = Map.of(
                    "id", user.getId().toString(),
                    "email", user.getEmail()
            );
            token.setAdditionalInformation(oidc);
        });
        return token;
    }
}
