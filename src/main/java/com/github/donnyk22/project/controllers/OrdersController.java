package com.github.donnyk22.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.services.orders.OrdersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrdersDto>> orders(@RequestBody @Valid OrderAddForm body) {
        OrdersDto result = ordersService.orders(body);
        ApiResponse<OrdersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Ordered successfully",
                result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<ApiResponse<OrdersDto>> payment(@PathVariable Integer id) {
        OrdersDto result = ordersService.payment(id);
        ApiResponse<OrdersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Order has successfully paid",
                result);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrdersDto>>> find() {
        List<OrdersDto> result = ordersService.find();
        ApiResponse<List<OrdersDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Orders fetched successfully",
                result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrdersDto>> findOne(@PathVariable Integer id) {
        OrdersDto result = ordersService.findOne(id);
        ApiResponse<OrdersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Order fetched successfully",
                result);
        return ResponseEntity.ok(response);
    }
}
