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
    private ReportsService reportsService;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<ReportsSalesDto>> sales() {
        ReportsSalesDto result = reportsService.sales();
        ApiResponse<ReportsSalesDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Sales data fetched successfully", result);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/bestseller")
    public ResponseEntity<ApiResponse<List<ReportsTopThreeSalesDto>>> bestSeller() {
        List<ReportsTopThreeSalesDto> result = reportsService.bestSeller();
        ApiResponse<List<ReportsTopThreeSalesDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Best seller data fetched successfully", result);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/prices")
    public ResponseEntity<ApiResponse<ReportsPricesDto>> prices() {
        ReportsPricesDto result = reportsService.prices();
        ApiResponse<ReportsPricesDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Prices data fetched successfully", result);
        return ResponseEntity.ok(response);
    }
}