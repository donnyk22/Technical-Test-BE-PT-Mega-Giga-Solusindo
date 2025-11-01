package com.github.donnyk22.project.services.orders;

import java.util.List;

import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.forms.OrderAddForm;

public interface OrdersService {
    OrdersDto orders(OrderAddForm body) throws Exception;
    OrdersDto payment(Integer id) throws Exception;
    List<OrdersDto> find() throws Exception;
    OrdersDto findOne(Integer id) throws Exception;
}
