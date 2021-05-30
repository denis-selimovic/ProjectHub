package ba.unsa.etf.nwt.notificationservice.config;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketsConfig {

    @Value("${pusher.app-id}")
    private String pusherApp;

    @Value("${pusher.key}")
    private String pusherKey;

    @Value("${pusher.secret}")
    private String pusherSecret;

    @Value("${pusher.cluster}")
    private String pusherCluster;

    @Bean
    public Pusher pusher() {
        Pusher pusher = new Pusher(pusherApp, pusherKey, pusherSecret);
        pusher.setCluster(pusherCluster);
        pusher.setEncrypted(true);
        return pusher;
    }
}
