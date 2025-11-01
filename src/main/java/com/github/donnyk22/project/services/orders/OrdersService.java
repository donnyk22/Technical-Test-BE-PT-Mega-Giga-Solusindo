package com.github.donnyk22.project.services.orders;

import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.models.forms.SearchForm;

public interface OrdersService {
    OrdersDto order(OrderAddForm body) throws Exception;
    OrdersDto payment(Integer id) throws Exception;
    FindResponse<OrdersDto> find(SearchForm body) throws Exception;
    OrdersDto findOne(Integer id) throws Exception;
}
