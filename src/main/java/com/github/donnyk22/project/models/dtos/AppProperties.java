package com.github.donnyk22.project.models.dtos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "app.jwt")
public class AppProperties {
    private String secret;
    private long ttlMinutes;
}
