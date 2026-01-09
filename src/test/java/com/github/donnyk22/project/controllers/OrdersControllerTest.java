package com.github.donnyk22.project.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.models.forms.OrderItemsAddForm;
import com.github.donnyk22.project.services.orders.OrdersService;

@Import(OrdersController.class)
@WebMvcTest(OrdersController.class)
public class OrdersControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrdersService ordersService;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= ORDER =================

    @Test
    void orders_shouldReturnOk_whenValidBody() throws Exception {
        OrderItemsAddForm item = new OrderItemsAddForm();
        item.setBookId(1);
        item.setQuantity(2);

        OrderAddForm form = new OrderAddForm();
        form.setItems(List.of(item));

        OrdersDto dto = new OrdersDto();
        dto.setId(1);

        when(ordersService.orders(any(OrderAddForm.class))).thenReturn(dto);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Ordered successfully"))
            .andExpect(jsonPath("$.data.id").value(1));
    }

    // ================= PAYMENT =================

    @Test
    void payment_shouldReturnOk() throws Exception {
        OrdersDto dto = new OrdersDto();
        dto.setId(1);

        when(ordersService.payment(1)).thenReturn(dto);

        mockMvc.perform(post("/api/orders/1/pay"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Order has successfully paid"));
    }

    // ================= FIND =================

    @Test
    void find_shouldReturnOk() throws Exception {
        OrdersDto dto = new OrdersDto();
        dto.setId(1);

        when(ordersService.find()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Orders fetched successfully"))
            .andExpect(jsonPath("$.data[0].id").value(1));
    }

    // ================= FIND ONE =================

    @Test
    void findOne_shouldReturnOk_whenExist() throws Exception {
        OrdersDto dto = new OrdersDto();
        dto.setId(1);

        when(ordersService.findOne(1)).thenReturn(dto);

        mockMvc.perform(get("/api/orders/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Order fetched successfully"))
            .andExpect(jsonPath("$.data.id").value(1));
    }

}
