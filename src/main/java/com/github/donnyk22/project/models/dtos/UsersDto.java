package com.github.donnyk22.project.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UsersDto {
    private Integer id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private String token;
    private List<OrdersDto> orderList;
}
