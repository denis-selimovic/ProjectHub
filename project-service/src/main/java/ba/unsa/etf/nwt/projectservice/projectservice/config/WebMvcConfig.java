package ba.unsa.etf.nwt.projectservice.projectservice.config;


import ba.unsa.etf.nwt.projectservice.projectservice.security.ResourceOwnerInjector;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ResourceOwnerInjector());
    }
}
