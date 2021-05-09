package ba.unsa.etf.nwt.projectservice.projectservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
public class RabbitMQConfig {

    @Configuration
    public static class ProjectNotificationQueueConfig {

        public static final String QUEUE_NAME = "project-notification-queue";
        public static final String EXCHANGE_NAME = "project-notification-exchange";
        public static final String ROUTING_KEY = "project-notification-routing-key";

        @Bean
        public Queue projectNotificationQueue() {
            return new Queue(QUEUE_NAME);
        }

        @Bean
        public DirectExchange projectNotificationExchange() {
            return new DirectExchange(EXCHANGE_NAME);
        }

        @Bean
        public Binding binding(Queue projectNotificationQueue, DirectExchange projectNotificationExchange) {
            return BindingBuilder
                    .bind(projectNotificationQueue)
                    .to(projectNotificationExchange)
                    .with(ROUTING_KEY);
        }
    }

    @Configuration
    public static class DeleteProjectQueueConfig {

        public static final String QUEUE_NAME = "delete-project-queue";
        public static final String EXCHANGE_NAME = "delete-project-exchange";
        public static final String ROUTING_KEY = "delete-project-routing-key";

        @Bean
        public Queue deleteProjectQueue() {
            return new Queue(QUEUE_NAME);
        }

        @Bean
        public DirectExchange deleteProjectExchange() {
            return new DirectExchange(EXCHANGE_NAME);
        }

        @Bean
        public Binding deleteProjectBinding(Queue deleteProjectQueue, DirectExchange deleteProjectExchange) {
            return BindingBuilder
                    .bind(deleteProjectQueue)
                    .to(deleteProjectExchange)
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