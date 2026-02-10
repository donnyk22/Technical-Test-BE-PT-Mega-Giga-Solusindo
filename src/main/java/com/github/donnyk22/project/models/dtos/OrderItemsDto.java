package com.github.donnyk22.project.models.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class OrderItemsDto {
    private Integer id;
    private Integer orderId;
    private Integer bookId;
    private Integer quantity;
    private BigDecimal price;
    private OrdersDto orderDetail;
    private BooksDto bookDetail;
}
