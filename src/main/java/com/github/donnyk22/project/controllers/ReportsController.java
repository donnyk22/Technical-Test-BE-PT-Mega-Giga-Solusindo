package com.github.donnyk22.project.controllers;

import java.util.List;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(
    name = "Reports",
    description = "Analytics and reporting APIs"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    @Operation(
        summary = "Get sales report [Admin Only]",
        description = "Retrieve sales and revenue statistics."
    )
    @PreAuthorize("hasAuthority(UserRoles.ADMIN)")
    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<ReportsSalesDto>> sales() {
        ReportsSalesDto result = reportsService.sales();
        ApiResponse<ReportsSalesDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Sales data fetched successfully", result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get best sellers [Admin Only]",
        description = "Retrieve three top-selling books."
    )
    @PreAuthorize("hasAuthority(UserRoles.ADMIN)")
    @GetMapping("/bestseller")
    public ResponseEntity<ApiResponse<List<ReportsTopThreeSalesDto>>> bestSeller() {
        List<ReportsTopThreeSalesDto> result = reportsService.bestSeller();
        ApiResponse<List<ReportsTopThreeSalesDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Best seller data fetched successfully", result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get price statistics [Admin Only]",
        description = "Retrieve book price statistics."
    )
    @PreAuthorize("hasAuthority(UserRoles.ADMIN)")
    @GetMapping("/prices")
    public ResponseEntity<ApiResponse<ReportsPricesDto>> prices() {
        ReportsPricesDto result = reportsService.prices();
        ApiResponse<ReportsPricesDto> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Prices data fetched successfully", result);
        return ResponseEntity.ok(response);
    }
}