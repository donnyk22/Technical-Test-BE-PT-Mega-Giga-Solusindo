package com.github.donnyk22.project.models.mappers;

import java.util.stream.Collectors;

import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.entities.Orders;

public class OrdersMapper {
    public static OrdersDto toBaseDto(Orders model){
        OrdersDto dto = new OrdersDto()
            .setId(model.getId())
            .setUserId(model.getUserId())
            .setTotalPrice(model.getTotalPrice())
            .setStatus(model.getStatus())
            .setCreatedAt(model.getCreatedAt());
        return dto;
    }

    public static OrdersDto toDetailDto(Orders model){
        OrdersDto dto = toBaseDto(model);
        dto.setUserDetail(UsersMapper.toBaseDto(model.getUser()))
            .setOrderItemList(model.getOrderItems()
            .stream()
            .map(OrderItemsMapper::toBaseDto)
            .collect(Collectors.toList()));
        return dto;
    }
}
