package com.github.donnyk22.project.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.services.experimental.asyncFuntion.AsyncFuncService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@Tag(
    name = "Experimental Async Funtion",
    description = "Async APIs for experimental purposes"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/experimental/async")
@Validated //@Validated is for validating @RequestParam, @PathVariable, @RequestHeader
public class ExperimentalAsyncFuncService {

    private final AsyncFuncService asyncFuncService;

    @Operation(
        summary = "Send dummy email",
        description = "Send dummy email and simulate the async process in the background."
    )
    @GetMapping("/send-email")
    public CompletableFuture<ResponseEntity<ApiResponse<String>>> sendDummyEmail(
            @RequestParam 
            @NotBlank(message = "Email is required") 
            @Email(message = "Invalid email format") String email) {
        return asyncFuncService.sendEmailDummy(email)
        .thenApply(result -> {
            ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Email sent successfully",
                result
            );
            return ResponseEntity.ok(response);
        })
        .exceptionally(ex -> {
            ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        });
    }

}
