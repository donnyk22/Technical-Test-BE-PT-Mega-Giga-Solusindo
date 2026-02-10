package com.github.donnyk22.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.WebSocketUserSessionDto;
import com.github.donnyk22.project.models.forms.ExperimentalWebSocketForm;
import com.github.donnyk22.project.models.forms.ExperimentalWebSocketUsersForm;
import com.github.donnyk22.project.services.experimental.websocket.WebSocketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(
    name = "Experimental WebSocket",
    description = "WebSocket management APIs for experimental purposes"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/experimental/ws")
public class ExperimentalWebSocketController {

    private final WebSocketService websocketService;

    @MessageMapping("/messages")
    @SendTo("/topic/messages")
    public ExperimentalWebSocketForm topic(ExperimentalWebSocketForm message) {
        return message;
    }

    @MessageMapping("/messages/users")
    @SendTo("/queue/messages")
    public ExperimentalWebSocketForm topicUsers(ExperimentalWebSocketForm message) {
        return message;
    }

    @Operation(
        summary = "Send message to all users",
        description = "Broadcast a message to all connected WebSocket users."
    )
    @PostMapping()
    public ResponseEntity<ApiResponse<ExperimentalWebSocketForm>> sendMessages(@RequestBody @Valid ExperimentalWebSocketForm message) {
        ExperimentalWebSocketForm result = websocketService.sendMessages(message);
        ApiResponse<ExperimentalWebSocketForm> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Message broadcasted successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Send message to specific users",
        description = "Send a message to specific WebSocket users."
    )
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<ExperimentalWebSocketForm>> sendMessagesToUsers(@RequestBody @Valid ExperimentalWebSocketUsersForm message) {
        ExperimentalWebSocketForm result = websocketService.sendMessagesToUsers(message);
        ApiResponse<ExperimentalWebSocketForm> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Message sent to specific users successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get active users [Admin Only]",
        description = "Retrieve all active WebSocket users."
    )
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/users/online")
    public ResponseEntity<ApiResponse<WebSocketUserSessionDto>> getActiveUsers() {
        WebSocketUserSessionDto result = websocketService.getActiveUsers();
        ApiResponse<WebSocketUserSessionDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "User sessions retrieved successfully",
            result);
        return ResponseEntity.ok(response);
    }

}
