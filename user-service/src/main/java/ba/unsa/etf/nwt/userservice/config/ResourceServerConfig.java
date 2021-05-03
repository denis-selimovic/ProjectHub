package ba.unsa.etf.nwt.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@EnableResourceServer
@Component
@RequiredArgsConstructor
@Order(-1)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) {
        configurer.tokenStore(tokenStore) ;
    }

    private final String[] unprotectedEndpoints = {
            "/oauth/token/**",
            "/api/v1/users/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/v3/**",
            "/swagger-ui/**",
            "/v1/swagger-ui/**",
            "/v1/api-docs/**",
            "/service-instances/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(unprotectedEndpoints)
                .permitAll()
                .anyRequest()
                .access("#oauth2.user")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
