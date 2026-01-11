package com.github.donnyk22.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.CategoriesDto;
import com.github.donnyk22.project.services.categories.CategoriesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(
    name = "Categories",
    description = "Category management APIs"
)
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @Operation(
        summary = "Create category [Admin Only]",
        description = "Create a new book category."
    )
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoriesDto>> create(@RequestParam String category) {
        CategoriesDto result = categoriesService.create(category);
        ApiResponse<CategoriesDto> response = new ApiResponse<>(HttpStatus.OK.value(), 
            "Category created successfully", 
            result);
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Get category detail [Admin Only]",
        description = "Retrieve category details by ID."
    )
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriesDto>> findOne(@PathVariable Integer id) {
        CategoriesDto result = categoriesService.findOne(id);
        ApiResponse<CategoriesDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Category Fetched successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get categories [Admin Only]",
        description = "Retrieve all categories."
    )
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriesDto>>> findAll() {
        List<CategoriesDto> result = categoriesService.findAll();
        ApiResponse<List<CategoriesDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Categories Fetched successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Update category [Admin Only]",
        description = "Update category name by ID."
    )
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriesDto>> update(@PathVariable Integer id, @RequestParam String category) {
        CategoriesDto result = categoriesService.update(id, category);
        ApiResponse<CategoriesDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Category edited successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Delete category [Admin Only]",
        description = "Delete category by ID."
    )
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriesDto>> delete(@PathVariable Integer id) {
        CategoriesDto result = categoriesService.delete(id);
        ApiResponse<CategoriesDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Category deleted successfully",
            result);
        return ResponseEntity.ok(response);
    }
}
