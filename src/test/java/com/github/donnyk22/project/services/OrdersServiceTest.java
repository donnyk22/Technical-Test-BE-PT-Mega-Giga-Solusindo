package com.github.donnyk22.project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ConflictException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.OrderItems;
import com.github.donnyk22.project.models.entities.Orders;
import com.github.donnyk22.project.models.entities.Users;
import com.github.donnyk22.project.models.enums.OrderStatus;
import com.github.donnyk22.project.models.enums.UserRoles;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.models.forms.OrderItemsAddForm;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.repositories.OrderItemsRepository;
import com.github.donnyk22.project.repositories.OrdersRepository;
import com.github.donnyk22.project.services.orders.OrdersServiceImpl;
import com.github.donnyk22.project.utils.AuthExtractUtil;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {
    
    @InjectMocks
    private OrdersServiceImpl ordersService;

    @Mock
    private AuthExtractUtil authExtractUtil;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    // ================= ORDER =================

    @Test
    void order_shouldThrowBadRequest_whenItemsNull() {
        OrderAddForm form = new OrderAddForm().setItems(null);

        assertThrows(
            BadRequestException.class,
            () -> ordersService.orders(form)
        );
    }

    @Test
    void orders_shouldThrowBadRequest_whenItemsIsEmpty() {
        OrderAddForm form = new OrderAddForm().setItems(List.of());

        assertThrows(
            BadRequestException.class,
            () -> ordersService.orders(form)
        );
    }

    @Test
    void order_shouldThrowNotFound_whenBookNotExists() {
        OrderItemsAddForm item = new OrderItemsAddForm()
            .setBookId(1)
            .setQuantity(1);

        when(authExtractUtil.getUserId()).thenReturn(1);

        when(ordersRepository.save(any(Orders.class)))
            .thenAnswer(invocation -> {
                Orders o = invocation.getArgument(0);
                o.setId(1);
                return o;
            });

        when(booksRepository.findById(1))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> ordersService.orders(
                new OrderAddForm().setItems(List.of(item))
            )
        );
    }

    @Test
    void orders_shouldThrowBadRequest_whenStockInsufficient() {
        OrderItemsAddForm item = new OrderItemsAddForm()
            .setBookId(1)
            .setQuantity(5);

        Books book = new Books()
            .setId(1)
            .setTitle("Out of Stock Book")
            .setPrice(BigDecimal.valueOf(100_000))
            .setStock(2);

        when(authExtractUtil.getUserId()).thenReturn(1);

        when(ordersRepository.save(any(Orders.class)))
            .thenAnswer(invocation -> {
                Orders o = invocation.getArgument(0);
                o.setId(1);
                return o;
            });

        when(booksRepository.findById(1))
            .thenReturn(Optional.of(book));

        assertThrows(
            BadRequestException.class,
            () -> ordersService.orders(
                new OrderAddForm().setItems(List.of(item))
            )
        );
    }

    @Test
    void orders_shouldCreateOrderSuccessfully() {
        // given
        OrderItemsAddForm item = new OrderItemsAddForm()
            .setBookId(1)
            .setQuantity(2);

        OrderAddForm form = new OrderAddForm()
            .setItems(List.of(item));

        Books book = new Books()
            .setId(1)
            .setTitle("Test Book")
            .setPrice(BigDecimal.valueOf(100_000))
            .setStock(10);

        when(authExtractUtil.getUserId()).thenReturn(1);

        when(ordersRepository.save(any(Orders.class)))
            .thenAnswer(invocation -> {
                Orders o = invocation.getArgument(0);
                o.setId(1);
                return o;
            });

        when(booksRepository.findById(1))
            .thenReturn(Optional.of(book));

        // when
        OrdersDto result = ordersService.orders(form);

        // then
        assertNotNull(result);

        verify(ordersRepository, times(2)).save(any(Orders.class));
        verify(orderItemsRepository).save(any(OrderItems.class));
        verify(booksRepository).findById(1);
    }

    // ================= PAYMENT =================

    @Test
    void payment_shouldThrowBadRequest_whenIdNull() {
        assertThrows(
            BadRequestException.class,
            () -> ordersService.payment(null)
        );
    }

    @Test
    void payment_shouldThrowNotFound_whenOrderNotExist() {
        when(ordersRepository.findById(1))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> ordersService.payment(1)
        );
    }

    @Test
    void payment_shouldThrowConflict_whenOrderAlreadyPaid() {
        Orders order = new Orders()
            .setId(1)
            .setStatus(OrderStatus.PAID.val());

        when(ordersRepository.findById(1))
            .thenReturn(Optional.of(order));

        assertThrows(
            ConflictException.class,
            () -> ordersService.payment(1)
        );
    }

    @Test
    void payment_shouldProcessPaymentSuccessfully() {
        // given
        Users user = new Users()
            .setId(1)
            .setName("User Test");

        Orders order = new Orders()
            .setId(1)
            .setUser(user)
            .setOrderItems(List.of())
            .setStatus(OrderStatus.PENDING.val());

        when(ordersRepository.findById(1))
            .thenReturn(Optional.of(order));

        when(ordersRepository.save(any(Orders.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        OrdersDto result = ordersService.payment(1);

        // then
        assertNotNull(result);
        assertEquals(OrderStatus.PAID.val(), order.getStatus());
    }

    // ================= FIND =================

    @Test
    void find_shouldReturnAllOrders_whenUserIsAdmin() {
        // given
        Orders order1 = new Orders().setId(1);
        Orders order2 = new Orders().setId(2);

        when(authExtractUtil.getUserRole())
            .thenReturn(UserRoles.ADMIN.val());

        when(ordersRepository.findAll())
            .thenReturn(List.of(order1, order2));

        // when
        List<OrdersDto> result = ordersService.find();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(authExtractUtil).getUserRole();
        verify(ordersRepository).findAll();
        verify(ordersRepository, never()).findByUserId(any());
    }

    @Test
    void find_shouldReturnUserOrders_whenUserIsNotAdmin() {
        // given
        Orders order = new Orders().setId(1);

        when(authExtractUtil.getUserRole())
            .thenReturn(UserRoles.USER.val());

        when(authExtractUtil.getUserId())
            .thenReturn(10);

        when(ordersRepository.findByUserId(10))
            .thenReturn(List.of(order));

        // when
        List<OrdersDto> result = ordersService.find();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(authExtractUtil).getUserRole();
        verify(authExtractUtil).getUserId();
        verify(ordersRepository).findByUserId(10);
        verify(ordersRepository, never()).findAll();
    }

    // ================= FIND ONE =================

    @Test
    void findOne_shouldThrowBadRequest_whenIdIsNull() {
        assertThrows(
            BadRequestException.class,
            () -> ordersService.findOne(null)
        );
    }

    @Test
    void findOne_shouldThrowNotFound_whenOrderDoesNotExist() {
        when(ordersRepository.findById(1))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> ordersService.findOne(1)
        );
    }

    @Test
    void findOne_shouldReturnDto_whenOrderExist() {
        // given
        Users user = new Users()
            .setId(1)
            .setName("User Test");

        Orders order = new Orders().setId(1)
            .setUser(user)
            .setOrderItems(List.of());

        when(ordersRepository.findById(1))
            .thenReturn(Optional.of(order));

        // when
        OrdersDto result = ordersService.findOne(1);

        // then
        assertNotNull(result);
        assertEquals(1, result.getId());
    }
        
}
