package com.github.donnyk22.project.services.orders;

import java.util.List;

import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.forms.OrderAddForm;

public interface OrdersService {
    OrdersDto orders(OrderAddForm body);
    OrdersDto payment(Integer id);
    List<OrdersDto> find();
    OrdersDto findOne(Integer id);
}
