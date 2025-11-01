package com.github.donnyk22.project.models.mappers;

import com.github.donnyk22.project.models.dtos.OrderItemsDto;
import com.github.donnyk22.project.models.entities.OrderItems;

public class OrderItemsMapper {
    public static OrderItemsDto toBaseDto(OrderItems model){
        OrderItemsDto dto = new OrderItemsDto()
            .setId(model.getId())
            .setOrderId(model.getOrderId())
            .setBookId(model.getBookId())
            .setQuantity(model.getQuantity())
            .setPrice(model.getPrice());
        return dto;
    }

    public static OrderItemsDto toDetailDto(OrderItems model){
        OrderItemsDto dto = toBaseDto(model);
        dto.setOrderDetail(OrdersMapper.toBaseDto(model.getOrder()))
            .setBookDetail(BooksMapper.toBaseDto(model.getBook()));
        return dto;
    }
}
