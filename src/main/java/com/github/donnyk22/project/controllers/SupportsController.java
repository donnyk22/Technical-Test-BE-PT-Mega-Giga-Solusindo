package com.github.donnyk22.project.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.services.supports.SupportsService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/supports")
public class SupportsController {

    @Autowired SupportsService supportsService;

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/redis-check-connection")
    public ResponseEntity<ApiResponse<String>> redisCheckConnection() {
        String result = supportsService.redisCheckConnection();
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Checking status success",
            result);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/user-check-login-credential")
    public ResponseEntity<ApiResponse<Map<String, String>>> orders() {
        Map<String, String> result = supportsService.checkUserLoginCredential();
        ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Checking active login credential success",
            result);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/system-get-bean-list")
    public ResponseEntity<ApiResponse<List<String>>> getBeanList() {
        List<String> result = supportsService.getBeanList();
        ApiResponse<List<String>> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Checking Bean list success",
            result);
        return ResponseEntity.ok(response);
    }
    
    
}
