package com.github.donnyk22.project.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrdersDto {
    private Integer id;
    private Integer UserId;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private UsersDto userDetail;
    private List<OrderItemsDto> orderItemList;
}
