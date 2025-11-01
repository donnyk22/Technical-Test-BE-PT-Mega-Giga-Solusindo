package com.github.donnyk22.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.donnyk22.project.models.dtos.ApiResponse;
import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.models.forms.BookFindForm;
import com.github.donnyk22.project.services.books.BooksService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired BooksService booksService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<BooksDto>> create(
        @Parameter(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookAddForm.class)))
        @RequestPart("data") @Valid BookAddForm form,
        @RequestPart(value = "file", required = false) MultipartFile image
        ) {
        try {
            BooksDto result = booksService.create(form, image);
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.OK.value(), "Book created successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<FindResponse<BooksDto>>> find(@ModelAttribute BookFindForm params) {
        try {
            FindResponse<BooksDto> result = booksService.find(params);
            ApiResponse<FindResponse<BooksDto>> response = new ApiResponse<FindResponse<BooksDto>>(
                HttpStatus.OK.value(), "Books fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<FindResponse<BooksDto>> response = new ApiResponse<FindResponse<BooksDto>>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BooksDto>> findOne(@PathVariable Integer id) {
        try {
            BooksDto result = booksService.findOne(id);
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.OK.value(), "Book fetched successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BooksDto>> update(@PathVariable Integer id,
        @Parameter(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEditForm.class)))
        @RequestPart("data") @Valid BookEditForm form, 
        @RequestPart(value = "file", required = false) MultipartFile image) {
        try {
            BooksDto result = booksService.update(id, form, image);
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.OK.value(), "Book updated successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BooksDto>> delete(@PathVariable Integer id) {
        try {
            BooksDto result = booksService.delete(id);
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.OK.value(), "Book deleted successfully", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<BooksDto> response = new ApiResponse<BooksDto>(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
}
