package com.github.donnyk22.project.models.dtos;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
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
