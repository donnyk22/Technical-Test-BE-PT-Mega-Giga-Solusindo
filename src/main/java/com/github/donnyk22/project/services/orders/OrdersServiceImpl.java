package com.github.donnyk22.project.services.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.models.forms.SearchForm;
import com.github.donnyk22.project.repositories.OrdersRepository;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private static final Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public OrdersDto order(OrderAddForm body) throws Exception {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'order'");
        return new OrdersDto();
    }

    @Override
    public OrdersDto payment(Integer id) throws Exception {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'payment'");
        return new OrdersDto();
    }

    @Override
    public FindResponse<OrdersDto> find(SearchForm body) throws Exception {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'find'");
        return new FindResponse<OrdersDto>();
    }

    @Override
    public OrdersDto findOne(Integer id) throws Exception {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'findOne'");
        return new OrdersDto();
    }
    
}
