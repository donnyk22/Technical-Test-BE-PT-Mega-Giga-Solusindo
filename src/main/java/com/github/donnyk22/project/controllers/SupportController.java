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
        try {
            String result = supportsService.redisCheckConnection();
            ApiResponse<String> response = new ApiResponse<String>(
                HttpStatus.OK.value(), "Checking status success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<String> response = new ApiResponse<String>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user-check-login")
    public ResponseEntity<ApiResponse<Map<String, String>>> orders() {
        try {
            Map<String, String> result = supportsService.checkUserLoginCredential();
            ApiResponse<Map<String, String>> response = new ApiResponse<Map<String, String>>(
                HttpStatus.OK.value(), "Checking active login credential success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Map<String, String>> response = new ApiResponse<Map<String, String>>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
}
