package com.github.donnyk22.project.services.experimental.messageBroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.donnyk22.project.models.constants.MsBrokerConstants;
import com.github.donnyk22.project.models.forms.ExperimentalMsBrokerForm;
import com.github.donnyk22.project.utils.ConverterUtil;

@Service
public class MsBrokerServiceImpl implements MsBrokerService {

    private static final Logger logger = LoggerFactory.getLogger(MsBrokerServiceImpl.class);

    private final RabbitTemplate rabbitTemplate;

    public MsBrokerServiceImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ExperimentalMsBrokerForm sendToTopicMessage(ExperimentalMsBrokerForm message) {
        rabbitTemplate.convertAndSend(
            MsBrokerConstants.MESSAGE_EXCHANGE,
            MsBrokerConstants.MESSAGE_ROUTING_KEY_MESSAGE,
            ConverterUtil.toBytes(message)
        );
        return message;
    }

    @Override
    public String sendToTopicText(String message) {
        rabbitTemplate.convertAndSend(
            MsBrokerConstants.MESSAGE_EXCHANGE,
            MsBrokerConstants.MESSAGE_ROUTING_KEY_TEXT,
            message
        );
        return message;
    }

    //Listeners

    @RabbitListener(queues = MsBrokerConstants.MESSAGE_QUEUE_MESSAGE)
    private void object(ExperimentalMsBrokerForm message) {
        logger.info("Received message object topic: {}", message);
    }

    @RabbitListener(queues = MsBrokerConstants.MESSAGE_QUEUE_TEXT)
    private void text(String message) {
        logger.info("Received message text topic: {}", message);
    }

    @RabbitListener(queues = MsBrokerConstants.MESSAGE_QUEUE_ALL)
    private void all(Object message) {
        logger.info("Received message all topic: {}", message);
    }
}