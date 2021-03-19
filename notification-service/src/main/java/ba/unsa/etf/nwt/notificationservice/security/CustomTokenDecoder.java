package ba.unsa.etf.nwt.notificationservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomTokenDecoder {

    @Value("${token.public-key}")
    private String publicKey;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        CustomTokenConverter converter = new CustomTokenConverter();
        converter.setVerifierKey(publicKey);
        return converter;
    }
}