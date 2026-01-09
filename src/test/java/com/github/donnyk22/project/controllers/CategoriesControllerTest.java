package com.github.donnyk22.project.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.github.donnyk22.project.models.dtos.CategoriesDto;
import com.github.donnyk22.project.services.categories.CategoriesService;

@Import(CategoriesController.class)
@WebMvcTest(CategoriesController.class)
public class CategoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriesService categoriesService;

    // ================= CREATE =================

    @Test
    void create_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(post("/api/categories")
                .with(csrf())
                .param("category", "Fantasy"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void create_shouldReturnOk_whenAdmin() throws Exception {
        CategoriesDto dto = new CategoriesDto();
        dto.setId(1);
        dto.setName("Fantasy");

        when(categoriesService.create("Fantasy")).thenReturn(dto);

        mockMvc.perform(post("/api/categories")
                .with(csrf())
                .param("category", "Fantasy"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Category created successfully"))
            .andExpect(jsonPath("$.data.id").value(1));
    }

    // ================= FIND ONE =================

    @Test
    void findOne_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(post("/api/categories").with(csrf()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void findOne_shouldReturnOk_whenAdmin() throws Exception {
        CategoriesDto dto = new CategoriesDto();
        dto.setId(1);

        when(categoriesService.findOne(1)).thenReturn(dto);

        mockMvc.perform(get("/api/categories/1").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Category Fetched successfully"))
            .andExpect(jsonPath("$.data.id").value(1));
    }

    // ================= FIND ALL =================
    
    @Test
    void findAll_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(get("/api/categories").with(csrf()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void findAll_shouldReturnOk_whenAdmin() throws Exception {
        CategoriesDto dto = new CategoriesDto();
        dto.setId(1);

        when(categoriesService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/categories").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Categories Fetched successfully"))
            .andExpect(jsonPath("$.data.length()").value(1));
    }

    // ================= UPDATE =================

    @Test
    void update_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(put("/api/categories/1")
                .with(csrf())
                .param("category", "Sci-Fi"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void update_shouldReturnOk_whenAdmin() throws Exception {
        CategoriesDto dto = new CategoriesDto();
        dto.setId(1);
        dto.setName("Sci-Fi");

        when(categoriesService.update(1, "Sci-Fi")).thenReturn(dto);

        mockMvc.perform(put("/api/categories/1")
                .with(csrf())
                .param("category", "Sci-Fi"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Category edited successfully"));
    }

    // ================= DELETE =================

    @Test
    void delete_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(delete("/api/categories/1").with(csrf()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void delete_shouldReturnOk_whenAdmin() throws Exception {
        CategoriesDto dto = new CategoriesDto();
        dto.setId(1);

        when(categoriesService.delete(1)).thenReturn(dto);

        mockMvc.perform(delete("/api/categories/1").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Category deleted successfully"));
    }
}
