package com.github.donnyk22.project.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> byte[] toBytes(T message) {
        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize message", e);
        }
    }
}
