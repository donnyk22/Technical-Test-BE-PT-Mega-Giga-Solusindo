package com.github.donnyk22.project.models.forms;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookAddForm {
    @NotBlank
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private Integer year;
    private Integer categoryId;
}