package ba.unsa.etf.nwt.taskservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class TokenGenerator {

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    public OAuth2AccessToken createAccessToken(final String clientId, final UUID id, final String email) {
        Collection<GrantedAuthority> authorities = Collections.emptySet();
        Set<String> resourceIds = Set.of("oauth2-resource");
        Set<String> scopes = Set.of("read", "write");
        Map<String, String> requestParameters = Collections.emptyMap();
        Set<String> responseTypes = Collections.emptySet();
        Map<String, Serializable> extensionProperties = Collections.emptyMap();

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId, authorities, true, scopes,
                resourceIds, null, responseTypes, extensionProperties);

        User userPrincipal = new User(email, "",
                true, true, true, true, authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,
                null, authorities);
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        auth.setAuthenticated(true);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(new JwtTokenStore(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancer(id, email));
        tokenServices.setAccessTokenValiditySeconds(600);
        return tokenServices.createAccessToken(auth);
    }


    private TokenEnhancer tokenEnhancer(UUID id, String email) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(List.of(
                new CustomTokenEnhancer(id, email),
                jwtAccessTokenConverter
        ));
        return tokenEnhancerChain;
    }
}
