package com.github.donnyk22.project.models.dtos;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class UsersDto {
    private Integer id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private String token;
    private Instant issuedAt;
    private Instant expiresAt;
    private List<OrdersDto> orderList;
}
