package ba.unsa.etf.nwt.projectservice.projectservice.config;


import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwnerInjector;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final HandlerMethodArgumentResolver resourceOwnerInjector;

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(resourceOwnerInjector);
    }
}
