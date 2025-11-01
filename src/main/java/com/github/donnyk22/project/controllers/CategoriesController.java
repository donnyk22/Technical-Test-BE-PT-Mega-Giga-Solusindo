package com.github.donnyk22.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.CategoriesDto;
import com.github.donnyk22.project.services.categories.CategoriesService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriesDto>> create(@RequestParam String category) {
        try {
            CategoriesDto result = categoriesService.create(category);
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.OK.value(), "Category created successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriesDto>> findOne(@PathVariable Integer id) {
        try {
            CategoriesDto result = categoriesService.findOne(id);
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.OK.value(), "Category Fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriesDto>>> findAll() {
        try {
            List<CategoriesDto> result = categoriesService.findAll();
            ApiResponse<List<CategoriesDto>> response = new ApiResponse<List<CategoriesDto>>(
                HttpStatus.OK.value(), "Categories Fetched successfully", result);
            return new ResponseEntity<ApiResponse<List<CategoriesDto>>>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<List<CategoriesDto>> response = new ApiResponse<List<CategoriesDto>>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<ApiResponse<List<CategoriesDto>>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriesDto>> update(@PathVariable Integer id, @RequestParam String category) {
        try {
            CategoriesDto result = categoriesService.update(id, category);
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.OK.value(), "Category edited successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriesDto>> delete(@PathVariable Integer id) {
        try {
            CategoriesDto result = categoriesService.delete(id);
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.OK.value(), "Category deleted successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<CategoriesDto> response = new ApiResponse<CategoriesDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
