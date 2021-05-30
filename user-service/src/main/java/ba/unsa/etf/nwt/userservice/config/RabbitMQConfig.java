package ba.unsa.etf.nwt.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    @Configuration
    public static class CreateUserQueueConfig {

        public static final String QUEUE_NAME = "create-user-queue";
        public static final String EXCHANGE_NAME = "create-user-exchange";
        public static final String ROUTING_KEY = "create-user-routing-key";

        @Bean
        public Queue createUserQueue() {
            return new Queue(QUEUE_NAME);
        }

        @Bean
        public DirectExchange createUserExchange() {
            return new DirectExchange(EXCHANGE_NAME);
        }

        @Bean
        public Binding binding(Queue createUserQueue, DirectExchange createUserExchange) {
            return BindingBuilder
                    .bind(createUserQueue)
                    .to(createUserExchange)
                    .with(ROUTING_KEY);
        }
    }

    @Configuration
    public static class CreateUserNotificationQueueConfig {

        public static final String QUEUE_NAME = "create-user-notif-queue";
        public static final String EXCHANGE_NAME = "create-user-notif-exchange";
        public static final String ROUTING_KEY = "create-user-notif-routing-key";

        @Bean
        public Queue createUserNotifQueue() {
            return new Queue(QUEUE_NAME);
        }

        @Bean
        public DirectExchange createUserNotifExchange() {
            return new DirectExchange(EXCHANGE_NAME);
        }

        @Bean
        public Binding bindingNotif(Queue createUserQueue, DirectExchange createUserExchange) {
            return BindingBuilder
                    .bind(createUserQueue)
                    .to(createUserExchange)
                    .with(ROUTING_KEY);
        }
    }

    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory, MessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter);
        return template;
    }
}
