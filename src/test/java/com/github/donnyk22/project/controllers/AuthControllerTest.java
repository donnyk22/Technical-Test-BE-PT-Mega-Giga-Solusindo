package com.github.donnyk22.project.controllers;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.donnyk22.project.models.dtos.UsersDto;
import com.github.donnyk22.project.models.forms.UserLoginForm;
import com.github.donnyk22.project.models.forms.UserRegisterForm;
import com.github.donnyk22.project.services.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;

@Import(AuthController.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void register_shouldReturnOk_whenValidRequest() throws Exception {
        UserRegisterForm form = new UserRegisterForm()
            .setName("Donny")
            .setEmail("donny@test.com")
            .setPassword("password")
            .setRePassword("password");

        UsersDto dto = new UsersDto()
            .setName("Donny")
            .setEmail("donny@test.com");

        when(authService.register(
            any(UserRegisterForm.class),
            any(HttpServletRequest.class)
        )).thenReturn(dto);

        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message")
                .value("Register successfully. Please login with your credential"))
            .andExpect(jsonPath("$.data.email").value("donny@test.com"));
    }

    @Test
    @WithMockUser
    void login_shouldReturnSuccessAndDto() throws Exception {
        when(authService.login(
            any(UserLoginForm.class),
            any(HttpServletRequest.class)
        )).thenReturn(new UsersDto());

        UserLoginForm form = new UserLoginForm()
            .setEmail("test@test.com")
            .setPassword("password");

        mockMvc.perform(post("/api/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Login successfully"))
            .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @WithMockUser
    void refresh_shouldReturnSuccessAndDto() throws Exception {
        when(authService.refresh())
            .thenReturn(new UsersDto());

        mockMvc.perform(post("/api/auth/refresh")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Credential refreshed successfully"))
            .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @WithMockUser
    void logout_shouldReturnSuccessAndBoolean() throws Exception {
        when(authService.logout(any(HttpServletRequest.class)))
            .thenReturn(true);

        mockMvc.perform(post("/api/auth/logout")
                .with(csrf())
                .header("Authorization", "Bearer token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Logout successfully"))
            .andExpect(jsonPath("$.data").value(true));
    }
    
}
