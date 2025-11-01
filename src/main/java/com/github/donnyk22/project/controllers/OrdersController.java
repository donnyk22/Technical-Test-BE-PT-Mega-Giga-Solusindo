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

    @Autowired OrdersService ordersService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<OrdersDto>> orders(@RequestBody @Valid OrderAddForm body) {
        try {
            OrdersDto result = ordersService.orders(body);
            ApiResponse<OrdersDto> response = new ApiResponse<OrdersDto>(
                HttpStatus.OK.value(), "Ordered successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<OrdersDto> response = new ApiResponse<OrdersDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<ApiResponse<OrdersDto>> payment(@PathVariable Integer id) {
        try {
            OrdersDto result = ordersService.payment(id);
            ApiResponse<OrdersDto> response = new ApiResponse<OrdersDto>(
                HttpStatus.OK.value(), "Order has successfully paid", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<OrdersDto> response = new ApiResponse<OrdersDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrdersDto>>> find() {
        try {
            List<OrdersDto> result = ordersService.find();
            ApiResponse<List<OrdersDto>> response = new ApiResponse<List<OrdersDto>>(
                HttpStatus.OK.value(), "Orders fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<List<OrdersDto>> response = new ApiResponse<List<OrdersDto>>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrdersDto>> findOne(@PathVariable Integer id) {
        try {
            OrdersDto result = ordersService.findOne(id);
            ApiResponse<OrdersDto> response = new ApiResponse<OrdersDto>(
                HttpStatus.OK.value(), "Order fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<OrdersDto> response = new ApiResponse<OrdersDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
