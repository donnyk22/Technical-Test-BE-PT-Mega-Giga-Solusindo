package com.github.donnyk22.project.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.services.books.BooksService;

@Import(BooksController.class)
@WebMvcTest(BooksController.class)
public class BooksController {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BooksService booksService;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= CREATE =================

    @Test
    void create_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(multipart("/api/books"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void create_shouldReturnOk_whenValidRequest() throws Exception {
        BookAddForm form = new BookAddForm();
        form.setTitle("Test Book");

        BooksDto dto = new BooksDto();
        dto.setId(1);
        dto.setTitle("Test Book");

        MockMultipartFile data = new MockMultipartFile(
            "data",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsBytes(form)
        );

        MockMultipartFile file = new MockMultipartFile(
            "file",
            "image.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "fake-image".getBytes()
        );

        when(booksService.create(any(), any())).thenReturn(dto);

        mockMvc.perform(multipart("/api/books")
                .file(data)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("Book created successfully"))
            .andExpect(jsonPath("$.data.id").value(1));
    }

    // ================= FIND =================

    @Test
    void find_shouldReturnOk() throws Exception {
        FindResponse<BooksDto> response = new FindResponse<>();
        response.setRecords(List.of(new BooksDto()));

        when(booksService.find(any())).thenReturn(response);

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Books fetched successfully"));
    }

    // ================= FIND ONE =================

    @Test
    void findOne_shouldReturnOk_whenExist() throws Exception {
        BooksDto dto = new BooksDto();
        dto.setId(1);

        when(booksService.findOne(1)).thenReturn(dto);

        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1));
    }

    // ================= UPDATE =================

    @Test
    void update_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(multipart("/api/books/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void update_shouldReturnOk() throws Exception {
        BookEditForm form = new BookEditForm();
        form.setTitle("Updated");

        BooksDto dto = new BooksDto();
        dto.setId(1);

        MockMultipartFile data = new MockMultipartFile(
            "data",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsBytes(form)
        );

        when(booksService.update(eq(1), any(), any())).thenReturn(dto);

        mockMvc.perform(multipart("/api/books/1")
                .file(data)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Book updated successfully"));
    }

    // ================= DELETE =================

    @Test
    void delete_shouldReturnUnauthorized_whenNotLoggedIn() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "admin")
    void delete_shouldReturnOk() throws Exception {
        BooksDto dto = new BooksDto();
        dto.setId(1);

        when(booksService.delete(1)).thenReturn(dto);

        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Book deleted successfully"));
    }
}
