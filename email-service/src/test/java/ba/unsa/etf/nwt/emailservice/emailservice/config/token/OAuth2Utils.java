package ba.unsa.etf.nwt.emailservice.emailservice.config.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class OAuth2Utils {

    @Value("${token.test.private-key}")
    private String privateKey;
    @Value("${token.test.password}")
    private String password;
    @Value("${token.test.alias}")
    private String alias;

    @Bean
    @Profile("test")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(privateKey),
                password.toCharArray()
        );
        accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
        return accessTokenConverter;
    }
}
