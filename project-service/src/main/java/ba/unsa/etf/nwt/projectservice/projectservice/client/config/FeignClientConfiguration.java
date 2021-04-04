package ba.unsa.etf.nwt.projectservice.projectservice.client.config;

import ba.unsa.etf.nwt.projectservice.projectservice.client.exception.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
