package com.github.donnyk22.project.models.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketUserSessionDetailDto {
    private String userId;
    private Set<String> sessions;
}
