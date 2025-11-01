package com.github.donnyk22.project.models.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BooksDto {
    private Integer id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private Integer year;
    private Integer categoryId;
    private String imageBase64;
    private CategoriesDto categoryDetail;
    private List<OrderItemsDto> orderItemList;
}
