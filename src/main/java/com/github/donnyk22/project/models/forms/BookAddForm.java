package com.github.donnyk22.project.models.forms;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class BookAddForm {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @NotNull(message = "Price is required")
    private BigDecimal price;
    @NotNull(message = "Stock is required")
    private Integer stock;
    @NotNull(message = "Year is required")
    private Integer year;
    @NotNull(message = "Category ID is required")
    private Integer categoryId;
}