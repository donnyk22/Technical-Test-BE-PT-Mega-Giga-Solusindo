package com.github.donnyk22.project.services.experimental.websocket;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import com.github.donnyk22.project.models.dtos.WebSocketUserSessionDetailDto;
import com.github.donnyk22.project.models.dtos.WebSocketUserSessionDto;
import com.github.donnyk22.project.models.forms.ExperimentalWebSocketForm;
import com.github.donnyk22.project.models.forms.ExperimentalWebSocketUsersForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //creates constructor automatically
public class WebsocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private final SimpUserRegistry simpUserRegistry;

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

    @Override
    public WebSocketUserSessionDto getActiveUsers() {
        List<WebSocketUserSessionDetailDto> users = simpUserRegistry.getUsers().stream()
            .map(user -> new WebSocketUserSessionDetailDto(
                user.getName(),
                user.getSessions()
                    .stream()
                    .map(SimpSession::getId)
                    .collect(Collectors.toSet())
            ))
            .collect(Collectors.toList());

        WebSocketUserSessionDto result = new WebSocketUserSessionDto()
            .setCount(simpUserRegistry.getUserCount())
            .setDetail(users);

        return result;
    }
    
}
