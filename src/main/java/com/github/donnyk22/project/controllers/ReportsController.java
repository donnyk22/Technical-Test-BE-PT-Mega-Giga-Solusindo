package com.github.donnyk22.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;
import com.github.donnyk22.project.models.dtos.ReportsTopThreeSalesDto;
import com.github.donnyk22.project.services.reports.ReportsService;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    ReportsService reportsService;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<ReportsSalesDto>> sales() {
        try {
            ReportsSalesDto result = reportsService.sales();
            ApiResponse<ReportsSalesDto> response = new ApiResponse<ReportsSalesDto>(
                HttpStatus.OK.value(), "Sales data fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<ReportsSalesDto> response = new ApiResponse<ReportsSalesDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/bestseller")
    public ResponseEntity<ApiResponse<List<ReportsTopThreeSalesDto>>> bestSeller() {
        try {
            List<ReportsTopThreeSalesDto> result = reportsService.bestSeller();
            ApiResponse<List<ReportsTopThreeSalesDto>> response = new ApiResponse<List<ReportsTopThreeSalesDto>>(
                HttpStatus.OK.value(), "Best seller data fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<List<ReportsTopThreeSalesDto>> response = new ApiResponse<List<ReportsTopThreeSalesDto>>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/prices")
    public ResponseEntity<ApiResponse<ReportsPricesDto>> prices() {
        try {
            ReportsPricesDto result = reportsService.prices();
            ApiResponse<ReportsPricesDto> response = new ApiResponse<ReportsPricesDto>(
                HttpStatus.OK.value(), "Prices data fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<ReportsPricesDto> response = new ApiResponse<ReportsPricesDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
}
