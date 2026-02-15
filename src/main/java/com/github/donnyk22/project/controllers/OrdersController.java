package com.github.donnyk22.project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Tag(
    name = "Orders",
    description = "Order processing and payment APIs"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Validated //@Validated is for validating @RequestParam, @PathVariable, @RequestHeader
public class OrdersController {

    private final OrdersService ordersService;

    @Operation(
        summary = "Create order",
        description = "Place a new order with selected items."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<OrdersDto>> orders(@RequestBody @Valid OrderAddForm body) {
        OrdersDto result = ordersService.orders(body);
        ApiResponse<OrdersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Ordered successfully",
                result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Pay order",
        description = "Process payment for an order (payment gateway simulation)."
    )
    @PostMapping("/{id}/pay")
    public ResponseEntity<ApiResponse<OrdersDto>> payment(@PathVariable @NotNull(message = "ID is required") Integer id) {
        OrdersDto result = ordersService.payment(id);
        ApiResponse<OrdersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Order has successfully paid",
                result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get orders",
        description = "Retrieve user orders or all orders for admin."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrdersDto>>> find() {
        List<OrdersDto> result = ordersService.find();
        ApiResponse<List<OrdersDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Orders fetched successfully",
                result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get order detail",
        description = "Retrieve order details by ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrdersDto>> findOne(@PathVariable @NotNull(message = "ID is required") Integer id) {
        OrdersDto result = ordersService.findOne(id);
        ApiResponse<OrdersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Order fetched successfully",
                result);
        return ResponseEntity.ok(response);
    }
}
