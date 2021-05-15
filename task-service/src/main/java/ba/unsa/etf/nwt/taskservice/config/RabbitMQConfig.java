package ba.unsa.etf.nwt.taskservice.config;

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
    public static class TaskNotificationQueueConfig {

        public static final String QUEUE_NAME = "task-notification-queue";
        public static final String EXCHANGE_NAME = "task-notification-exchange";
        public static final String ROUTING_KEY = "task-notification-routing-key";

        @Bean
        public Queue taskNotificationQueue() {
            return new Queue(QUEUE_NAME);
        }

        @Bean
        public DirectExchange taskNotificationExchange() {
            return new DirectExchange(EXCHANGE_NAME);
        }

        @Bean
        public Binding binding(Queue taskNotificationQueue, DirectExchange taskNotificationExchange) {
            return BindingBuilder
                    .bind(taskNotificationQueue)
                    .to(taskNotificationExchange)
                    .with(ROUTING_KEY);
        }
    }

    @Configuration
    public static class RevertProjectDeleteQueueConfig {

        public static final String QUEUE_NAME = "revert-project-delete-queue";
        public static final String EXCHANGE_NAME = "revert-project-delete-exchange";
        public static final String ROUTING_KEY = "revert-project-delete-routing-key";

        @Bean
        public Queue revertProjectDeleteQueue() {
            return new Queue(QUEUE_NAME);
        }

        @Bean
        public DirectExchange revertProjectDeleteExchange() {
            return new DirectExchange(EXCHANGE_NAME);
        }

        @Bean
        public Binding revertProjectDeleteBinding(Queue revertProjectDeleteQueue, DirectExchange revertProjectDeleteExchange) {
            return BindingBuilder
                    .bind(revertProjectDeleteQueue)
                    .to(revertProjectDeleteExchange)
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
