package com.github.donnyk22.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.UsersDto;
import com.github.donnyk22.project.models.forms.UserLoginForm;
import com.github.donnyk22.project.models.forms.UserRegisterForm;
import com.github.donnyk22.project.services.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UsersDto>> register(@RequestBody @Valid UserRegisterForm form) {
        UsersDto result = authService.register(form);
        ApiResponse<UsersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Register successfully. Please login with your credential",
            result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UsersDto>> login(@RequestBody @Valid UserLoginForm form) {
        UsersDto result = authService.login(form);
        ApiResponse<UsersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Login successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<UsersDto>> refresh() {
        UsersDto result = authService.refresh();
        ApiResponse<UsersDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Credential refreshed successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Boolean>> logout(HttpServletRequest request) {
            Boolean result = authService.logout(request);
            ApiResponse<Boolean> response = new ApiResponse<>(HttpStatus.OK.value(),
                "Logout successfully",
                result);
            return ResponseEntity.ok(response);
    }

}
