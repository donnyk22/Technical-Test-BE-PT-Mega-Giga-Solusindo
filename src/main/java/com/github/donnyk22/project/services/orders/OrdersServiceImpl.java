package com.github.donnyk22.project.services.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.OrderItems;
import com.github.donnyk22.project.models.entities.Orders;
import com.github.donnyk22.project.models.enums.OrderStatus;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.models.forms.OrderItemsAddForm;
import com.github.donnyk22.project.models.mappers.OrdersMapper;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.repositories.OrderItemsRepository;
import com.github.donnyk22.project.repositories.OrdersRepository;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private static final Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Autowired OrdersRepository ordersRepository;
    @Autowired BooksRepository booksRepository;
    @Autowired OrderItemsRepository orderItemsRepository;

    @Override
    public OrdersDto orders(OrderAddForm body) throws Exception {
        if (body.getItems().isEmpty()){
            logger.error("Order at least one item");
            throw new Exception("Order at least one item");
        }
        Orders order = new Orders();
        order.setUserId(2); //TODO: need fix from JWT
        order.setStatus(OrderStatus.PENDING.val())
            .setTotalPrice(BigDecimal.ZERO)
            .setCreatedAt(LocalDateTime.now());
        ordersRepository.save(order);

        List<OrderItems> orderItemList = new ArrayList<>();

        for (OrderItemsAddForm item: body.getItems()) {
            @SuppressWarnings("null")
            Books book = booksRepository.findById(item.getBookId()).orElse(null);
            if(book == null){
                logger.error("Book not found: " + item.getBookId());
                throw new Exception("Book not found");
            }
            if(book.getStock() < item.getQuantity()){
                throw new Exception("Sorry, the book stock is only "+item.getQuantity()+" left");
            }
            OrderItems orderItems = new OrderItems();
            orderItems.setOrderId(order.getId());
            orderItems.setBookId(book.getId());
            orderItems.setQuantity(item.getQuantity())
                .setPrice(book.getPrice());
            orderItemsRepository.save(orderItems);

            orderItemList.add(orderItems);

            BigDecimal totalPrice = book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            order.setTotalPrice(order.getTotalPrice().add(totalPrice));
        }
        ordersRepository.save(order);
        order.setOrderItems(orderItemList);

        return OrdersMapper.toDetailDto(order);
    }

    @Override
    public OrdersDto payment(Integer id) throws Exception {
        if (id == null){
            logger.error("Id is required");
            throw new Exception("Id is required");
        }
        Orders order = ordersRepository.findById(id).orElse(null);
        if(order == null){
            logger.error("Order data is not found");
            throw new Exception("Order data is not found");
        }
        if(order.getStatus().equals(OrderStatus.PAID.val())){
            logger.error("Order is already paid");
            throw new Exception("Order is already paid");
        }
        order.setStatus(OrderStatus.PAID.val());
        ordersRepository.save(order);
        return OrdersMapper.toDetailDto(order);
    }

    @Override
    public List<OrdersDto> find() throws Exception {
        //TODO: filter by JWT user roles
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream()
            .map(OrdersMapper::toBaseDto)
            .collect(Collectors
            .toList());
    }

    @Override
    public OrdersDto findOne(Integer id) throws Exception {
        if(id == null){
            logger.error("Id is required");
            throw new Exception("Id is required");
        }
        Orders order = ordersRepository.findById(id).orElse(null);
        if(order == null){
            logger.error("Order data is not found");
            throw new Exception("Order data is not found");
        }
        return OrdersMapper.toDetailDto(order);
    }
    
}
