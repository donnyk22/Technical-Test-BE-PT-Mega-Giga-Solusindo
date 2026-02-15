package com.github.donnyk22.project.services.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ConflictException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.OrderItems;
import com.github.donnyk22.project.models.entities.Orders;
import com.github.donnyk22.project.models.enums.OrderStatus;
import com.github.donnyk22.project.models.constants.UserRoles;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.models.forms.OrderItemsAddForm;
import com.github.donnyk22.project.models.mappers.OrdersMapper;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.repositories.OrderItemsRepository;
import com.github.donnyk22.project.repositories.OrdersRepository;
import com.github.donnyk22.project.utils.AuthExtractUtil;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final BooksRepository booksRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final AuthExtractUtil authExtractUtil;

    @Override
    public OrdersDto orders(OrderAddForm body) {
        Orders order = new Orders();
        order.setUserId(authExtractUtil.getUserId());
        order.setStatus(OrderStatus.PENDING.val())
            .setTotalPrice(BigDecimal.ZERO)
            .setCreatedAt(LocalDateTime.now());
        ordersRepository.save(order);

        List<OrderItems> orderItemList = new ArrayList<>();
        for (OrderItemsAddForm item : body.getItems()) {
            Books book = booksRepository.findById(item.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + item.getBookId()));
            if (book.getStock() < item.getQuantity()) {
                throw new BadRequestException("Insufficient stock for book: " + book.getTitle());
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
    public OrdersDto payment(Integer id) {
        Orders order = ordersRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        if (OrderStatus.PAID.val().equals(order.getStatus())) {
            throw new ConflictException("Order is already paid");
        }
        order.setStatus(OrderStatus.PAID.val());
        ordersRepository.save(order);
        return OrdersMapper.toDetailDto(order);
    }

    @Override
    public List<OrdersDto> find() {
        List<Orders> orders;
        if (UserRoles.ADMIN.equals(authExtractUtil.getUserRole())) {
            orders = ordersRepository.findAll();
        } else {
            orders = ordersRepository.findByUserId(authExtractUtil.getUserId());
        }
        return orders.stream()
            .map(OrdersMapper::toBaseDto)
            .toList();
    }

    @Override
    @Cacheable(value = "order", key = "#id")
    public OrdersDto findOne(Integer id) {
        Orders order = ordersRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        return OrdersMapper.toDetailDto(order);
    }
}
