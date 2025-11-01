package com.github.donnyk22.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.dtos.OrdersDto;
import com.github.donnyk22.project.models.forms.OrderAddForm;
import com.github.donnyk22.project.models.forms.SearchForm;
import com.github.donnyk22.project.services.orders.OrdersService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired OrdersService ordersService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<OrdersDto>> order(@RequestBody OrderAddForm body) {
        try {
            OrdersDto result = ordersService.order(body);
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
    public ResponseEntity<ApiResponse<FindResponse<OrdersDto>>> find(@RequestBody SearchForm body) {
        try {
            FindResponse<OrdersDto> result = ordersService.find(body);
            ApiResponse<FindResponse<OrdersDto>> response = new ApiResponse<FindResponse<OrdersDto>>(
                HttpStatus.OK.value(), "Orders fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<FindResponse<OrdersDto>> response = new ApiResponse<FindResponse<OrdersDto>>(
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
