package com.github.donnyk22.project.configurations;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.donnyk22.project.models.dtos.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Handle @Valid/jakarta.validation.constraints form
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .collect(Collectors.joining(", "));
        ApiResponse<Object> response = new ApiResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            errorMessage,
            null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //Handle max file upload error
    @ExceptionHandler(org.springframework.web.multipart.MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxSizeException() {
        ApiResponse<Object> response = new ApiResponse<>(
            HttpStatus.BAD_REQUEST.value(),
            "File too large",
            null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //Custom any error message
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Invalid data. Please check your input.";
        Throwable root = getRootCause(ex);
        if (root != null && root.getMessage() != null) {
            String msg = root.getMessage().toUpperCase();
            if (msg.contains("CONSTRAINT")) {
                // Generic safe message for any constraint violation
                message = "Invalid reference or constraint violation";
            }
        }
        ApiResponse<Object> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                message,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Throwable getRootCause(Throwable t) {
        Throwable root = t;
        while (root != null && root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        return root;
    }

    //Handle any global error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        ApiResponse<Object> response = new ApiResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage(),
            null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
