package com.github.donnyk22.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.forms.ExperimentalMsBrokerForm;
import com.github.donnyk22.project.services.experimental.messageBroker.MsBrokerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Tag(
    name = "Experimental Message Broker",
    description = "Message Broker management APIs for experimental purposes"
)
@RestController
@RequestMapping("/api/experimental/ms-broker")
public class ExperimentalMsBrokerController {

    @Autowired MsBrokerService msBrokerService;

    @Operation(
        summary = "Send message to object topic",
        description = "Send a message to object topic."
    )
    @PostMapping("/topic/object")
    public ResponseEntity<ApiResponse<ExperimentalMsBrokerForm>> sendToTopicObject(@RequestBody @Valid ExperimentalMsBrokerForm object) {
        ExperimentalMsBrokerForm result = msBrokerService.sendToTopicObject(object);
        ApiResponse<ExperimentalMsBrokerForm> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Message sent to topic object successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Send message to text topic",
        description = "Send a message to text topic."
    )
    @PostMapping("/topic/text")
    public ResponseEntity<ApiResponse<String>> sendToTopicText(@RequestBody @Valid @NotBlank(message = "message is required") String text) {
        String result = msBrokerService.sendToTopicText(text);
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Message sent to topic text successfully",
            result);
        return ResponseEntity.ok(response);
    }
}
