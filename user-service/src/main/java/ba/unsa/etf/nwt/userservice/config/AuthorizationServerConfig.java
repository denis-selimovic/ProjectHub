package ba.unsa.etf.nwt.userservice.config;

import ba.unsa.etf.nwt.userservice.controller.TokenController;
import ba.unsa.etf.nwt.userservice.security.CustomTokenEnhancer;
import ba.unsa.etf.nwt.userservice.service.AuthService;
import ba.unsa.etf.nwt.userservice.service.UserService;
import ba.unsa.etf.nwt.userservice.utility.OAuth2Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
    private final AuthService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2Utils oAuth2Utils;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(authService)
                .tokenEnhancer(tokenEnhancerChain());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        clientDetailsServiceConfigurer.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Primary
    public TokenEndpoint tokenEndpoint(AuthorizationServerEndpointsConfiguration conf) {
        TokenEndpoint tokenEndpoint = new TokenController();
        tokenEndpoint.setClientDetailsService(conf.getEndpointsConfigurer().getClientDetailsService());
        tokenEndpoint.setProviderExceptionHandler(conf.getEndpointsConfigurer().getExceptionTranslator());
        tokenEndpoint.setTokenGranter(conf.getEndpointsConfigurer().getTokenGranter());
        tokenEndpoint.setOAuth2RequestFactory(conf.getEndpointsConfigurer().getOAuth2RequestFactory());
        tokenEndpoint.setOAuth2RequestValidator(conf.getEndpointsConfigurer().getOAuth2RequestValidator());
        tokenEndpoint.setAllowedRequestMethods(conf.getEndpointsConfigurer().getAllowedTokenEndpointRequestMethods());
        return tokenEndpoint;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(oAuth2Utils.privateKey),
                oAuth2Utils.password.toCharArray()
        );
        accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(oAuth2Utils.alias));
        return accessTokenConverter;
    }

    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(List.of(
                new CustomTokenEnhancer(userService),
                jwtAccessTokenConverter()
        ));
        return tokenEnhancerChain;
    }
}
