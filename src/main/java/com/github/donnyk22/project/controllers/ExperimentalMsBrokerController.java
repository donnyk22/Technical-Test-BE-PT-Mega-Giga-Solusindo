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

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/experimental/rabbitmq")
public class ExperimentalMsBrokerController {

    @Autowired MsBrokerService msBrokerService;

    @PostMapping("/topic/message")
    public ResponseEntity<ApiResponse<ExperimentalMsBrokerForm>> sendToTopicMessage(@RequestBody @Valid ExperimentalMsBrokerForm message) {
        ExperimentalMsBrokerForm result = msBrokerService.sendToTopicMessage(message);
        ApiResponse<ExperimentalMsBrokerForm> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Message sent to topic object successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/topic/text")
    public ResponseEntity<ApiResponse<String>> sendToTopicText(@RequestBody @Valid @NotBlank(message = "message is required") String message) {
        String result = msBrokerService.sendToTopicText(message);
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Message sent to topic text successfully",
            result);
        return ResponseEntity.ok(response);
    }
}
