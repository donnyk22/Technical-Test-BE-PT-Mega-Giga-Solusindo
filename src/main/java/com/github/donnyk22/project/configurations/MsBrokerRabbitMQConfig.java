package com.github.donnyk22.project.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.donnyk22.project.models.constants.MsBrokerConstants;

@Configuration
public class MsBrokerRabbitMQConfig {

    @Bean
    public TopicExchange messageExchange() {
        return new TopicExchange(MsBrokerConstants.MESSAGE_EXCHANGE, true, false);
    }

    @Bean
    public Queue messageObject() {
        return QueueBuilder
            .durable(MsBrokerConstants.MESSAGE_QUEUE_OBJECT)
            .build();
    }

    @Bean
    public Queue messageText() {
        return QueueBuilder
            .durable(MsBrokerConstants.MESSAGE_QUEUE_TEXT)
            .build();
    }

    @Bean
    public Queue messageAll() {
        return QueueBuilder
            .durable(MsBrokerConstants.MESSAGE_QUEUE_ALL)
            .build();
    }

    @Bean
    public Binding messageObjectSent(@Qualifier("messageObject") Queue messageObjectQueue, TopicExchange messageExchange) {
        return BindingBuilder
            .bind(messageObjectQueue)
            .to(messageExchange)
            .with(MsBrokerConstants.MESSAGE_ROUTING_KEY_OBJECT);
    }

    @Bean
    public Binding messageTextSent(@Qualifier("messageText") Queue messageTextQueue, TopicExchange messageExchange) {
        return BindingBuilder
            .bind(messageTextQueue)
            .to(messageExchange)
            .with(MsBrokerConstants.MESSAGE_ROUTING_KEY_TEXT);
    }

    @Bean
    public Binding messageAllSent(@Qualifier("messageAll") Queue messageAllQueue, TopicExchange messageExchange) {
        return BindingBuilder
            .bind(messageAllQueue)
            .to(messageExchange)
            .with(MsBrokerConstants.MESSAGE_ROUTING_KEY_ALL);
    }

}