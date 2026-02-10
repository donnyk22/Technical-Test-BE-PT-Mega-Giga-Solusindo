package com.github.donnyk22.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(
    name = "Books",
    description = "Book management APIs"
)
@AllArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService booksService;

    @Operation(
        summary = "Create book [Admin Only]",
        description = "Create a new book with optional cover image."
    )
    @PreAuthorize("hasRole('admin')")
    // @PreAuthorize("hasAuthority('BOOK_CREATE')") // Example with authority
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<BooksDto>> create(
        @Parameter(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookAddForm.class)))
        @RequestPart("data") @Valid BookAddForm form,
        @RequestPart(value = "file", required = false) MultipartFile image
        ) {
        BooksDto result = booksService.create(form, image);
        ApiResponse<BooksDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Book created successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get books",
        description = "Retrieve books with pagination and filters."
    )
    @GetMapping()
    public ResponseEntity<ApiResponse<FindResponse<BooksDto>>> find(@ModelAttribute BookFindForm params) {
        FindResponse<BooksDto> result = booksService.find(params);
        ApiResponse<FindResponse<BooksDto>> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Books fetched successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Get book detail",
        description = "Retrieve book details by ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BooksDto>> findOne(@PathVariable Integer id) {
        BooksDto result = booksService.findOne(id);
        ApiResponse<BooksDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Book fetched successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Update book [Admin Only]",
        description = "Update book data and optional cover image."
    )
    @PreAuthorize("hasRole('admin')")
    // @PreAuthorize("hasAuthority('BOOK_UPDATE')") // Example with authority
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BooksDto>> update(@PathVariable Integer id,
        @Parameter(required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEditForm.class)))
        @RequestPart("data") @Valid BookEditForm form, 
        @RequestPart(value = "file", required = false) MultipartFile image) {
        BooksDto result = booksService.update(id, form, image);
        ApiResponse<BooksDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Book updated successfully",
            result);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Delete book [Admin Only]",
        description = "Delete a book by ID."
    )
    @PreAuthorize("hasRole('admin')")
    // @PreAuthorize("hasAuthority('BOOK_DELETE')") // Example with authority
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BooksDto>> delete(@PathVariable Integer id) {
        BooksDto result = booksService.delete(id);
        ApiResponse<BooksDto> response = new ApiResponse<>(HttpStatus.OK.value(),
            "Book deleted successfully",
            result);
        return ResponseEntity.ok(response);
    }
    
}
