package com.github.donnyk22.project.configurations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.donnyk22.project.models.constants.MsBrokerConstants;

@Configuration
public class MsBrokerRabbitMQConfig {

    // exchange is a gateway between producers and queues
    // Producer A  \
    // Producer B   --->  EXCHANGE (1) ---> Queues
    // Producer C  /
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

    // “Jika ada pesan masuk ke exchange messageExchange dengan routing key MESSAGE_ROUTING_KEY_OBJECT, maka masukkan pesan itu ke queue messageObjectQueue.”
    // Satu key bisa ke banyak queue, dan satu queue bisa terkoneksi ke banyak key (many to many)
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

    // =================== Job-related queues ===================

    @Value("${app.async.max-queue}")
    private Integer MAX_QUEUE;

    @Bean
    public Queue jobQueue() {
        Map<String, Object> args = new HashMap<>();
        // max messages stored in queue
        args.put("x-max-length", MAX_QUEUE);
        // if queue is full, drop oldest message
        // other options: reject-publish, drop-head
        args.put("x-overflow", "reject-publish");
        return new Queue(MsBrokerConstants.JOB_QUEUE, true, false, false, args);
    }

    @Bean
    public TopicExchange jobExchange() {
        return new TopicExchange(MsBrokerConstants.JOB_EXCHANGE, true, false);
    }

    @Bean
    public Binding jobSent(Queue jobQueue, TopicExchange jobExchange) {
        return BindingBuilder
            .bind(jobQueue)
            .to(jobExchange)
            .with(MsBrokerConstants.JOB_ROUTING_KEY);
    }

}