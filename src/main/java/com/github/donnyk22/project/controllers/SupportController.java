package com.github.donnyk22.project.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.services.supports.SupportsService;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    @Autowired SupportsService supportsService;

    @PostMapping("/redis-check-connection")
    public ResponseEntity<ApiResponse<String>> redisCheckConnection() {
        String result = supportsService.redisCheckConnection();
        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Checking status success",
            result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user-check-login-credential")
    public ResponseEntity<ApiResponse<Map<String, String>>> orders() {
        Map<String, String> result = supportsService.checkUserLoginCredential();
        ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Checking active login credential success",
            result);
        return ResponseEntity.ok(response);
    }
    
}
