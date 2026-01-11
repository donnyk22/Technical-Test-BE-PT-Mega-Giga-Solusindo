package com.github.donnyk22.project.services.experimental.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.github.donnyk22.project.models.forms.ExperimentalWebSocketForm;
import com.github.donnyk22.project.models.forms.ExperimentalWebSocketUsersForm;

@Service
public class WebsocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebsocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public ExperimentalWebSocketForm sendMessages(ExperimentalWebSocketForm message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
        return message;
    }

    @Override
    public ExperimentalWebSocketForm sendMessagesToUsers(ExperimentalWebSocketUsersForm message) {
        for (Integer userId: message.getUserIds()) {
            messagingTemplate.convertAndSendToUser(
                Integer.toString(userId),
                "/queue/messages",
                new ExperimentalWebSocketForm(message.getSubject(), message.getContent())
            );
        }
        return new ExperimentalWebSocketForm(message.getSubject(), message.getContent());
    }
    
}
