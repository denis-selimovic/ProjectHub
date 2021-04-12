package ba.unsa.etf.nwt.notificationservice.client.config;

import ba.unsa.etf.nwt.notificationservice.client.exception.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
