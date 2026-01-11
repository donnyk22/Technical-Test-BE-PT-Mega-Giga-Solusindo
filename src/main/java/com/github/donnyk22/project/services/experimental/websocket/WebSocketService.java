package com.github.donnyk22.project.services.experimental.websocket;

import com.github.donnyk22.project.models.forms.ExperimentalWebSocketForm;
import com.github.donnyk22.project.models.forms.ExperimentalWebSocketUsersForm;

public interface WebSocketService {
    ExperimentalWebSocketForm sendMessages(ExperimentalWebSocketForm message);
    ExperimentalWebSocketForm sendMessagesToUsers(ExperimentalWebSocketUsersForm message);
}
