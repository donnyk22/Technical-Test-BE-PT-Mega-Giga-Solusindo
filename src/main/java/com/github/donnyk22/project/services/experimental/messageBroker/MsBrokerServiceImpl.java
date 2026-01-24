package com.github.donnyk22.project.services.experimental.messageBroker;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
    public ExperimentalMsBrokerForm sendToTopicObject(ExperimentalMsBrokerForm object) {
        rabbitTemplate.convertAndSend(
            MsBrokerConstants.MESSAGE_EXCHANGE,
            MsBrokerConstants.MESSAGE_ROUTING_KEY_OBJECT,
            ConverterUtil.objectToBytes(object)
        );
        return object;
    }

    @Override
    public String sendToTopicText(String text) {
        rabbitTemplate.convertAndSend(
            MsBrokerConstants.MESSAGE_EXCHANGE,
            MsBrokerConstants.MESSAGE_ROUTING_KEY_TEXT,
            text
        );
        return text;
    }

    //Listeners

    @RabbitListener(queues = MsBrokerConstants.MESSAGE_QUEUE_OBJECT)
    private void object(byte[] object) {
        logger.info("Received message object topic: {}", ConverterUtil.bytesToString(object));
    }

    @RabbitListener(queues = MsBrokerConstants.MESSAGE_QUEUE_TEXT)
    private void text(String text) {
        logger.info("Received message text topic: {}", text);
    }

    @RabbitListener(queues = MsBrokerConstants.MESSAGE_QUEUE_ALL)
    public void all(Message message) {
        byte[] body = message.getBody();
        MessageProperties props = message.getMessageProperties();

        String str = ConverterUtil.bytesToString(body);

        if ("application/octet-stream".equals(props.getContentType())) {
            logger.info("Received message object via all topic listener: {}", str);
            return;
        }

        if ("text/plain".equals(props.getContentType())) {
            logger.info("Received message text via all topic listener: {}", str);
        }
    }
}