package com.github.donnyk22.project.models.dtos;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WebSocketUserSessionDto {
    private Integer count;
    private List<WebSocketUserSessionDetailDto> detail;
}
