package com.github.donnyk22.project.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.github.donnyk22.project.models.dtos.ReportsPricesDto;
import com.github.donnyk22.project.models.dtos.ReportsSalesDto;
import com.github.donnyk22.project.models.dtos.ReportsTopThreeSalesDto;
import com.github.donnyk22.project.services.reports.ReportsService;

@Import(ReportsController.class)
@WebMvcTest(ReportsController.class)
public class ReportsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportsService reportsService;

    // ================= SALES =================

    @Test
    public void sales_shouldAccessUnauthorized_whenNotLogin() throws Exception {
        mockMvc.perform(get("/api/reports/sales"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void sales_shouldAccessAuthorized_whenNotLoginAsAdmin() throws Exception {
        when(reportsService.sales()).thenReturn(new ReportsSalesDto());
        mockMvc.perform(get("/api/reports/sales"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Sales data fetched successfully"))
            .andExpect(jsonPath("$.data").exists());
    }

    // ================= BESTSELLER =================

    @Test
    public void bestSeller_shouldAccessUnauthorized_whenNotLogin() throws Exception {
        mockMvc.perform(get("/api/reports/bestseller"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void bestSeller_shouldAccessAuthorized_whenNotLoginAsAdmin() throws Exception {
        when(reportsService.bestSeller()).thenReturn(new ArrayList<ReportsTopThreeSalesDto>());
        mockMvc.perform(get("/api/reports/bestseller"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Best seller data fetched successfully"))
            .andExpect(jsonPath("$.data").exists());
    }

    // ================= PRICES =================

    @Test
    public void prices_shouldAccessUnauthorized_whenNotLogin() throws Exception {
        mockMvc.perform(get("/api/reports/prices"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    public void prices_shouldAccessAuthorized_whenNotLoginAsAdmin() throws Exception {
        when(reportsService.prices()).thenReturn(new ReportsPricesDto());
        mockMvc.perform(get("/api/reports/prices"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Prices data fetched successfully"))
            .andExpect(jsonPath("$.data").exists());
    }
}
