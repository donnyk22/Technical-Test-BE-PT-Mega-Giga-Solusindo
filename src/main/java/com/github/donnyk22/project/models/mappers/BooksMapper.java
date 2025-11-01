package com.github.donnyk22.project.models.mappers;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.entities.Books;

public class BooksMapper {
    public static BooksDto toBaseDto(Books model){
        BooksDto dto = new BooksDto()
            .setId(model.getId())
            .setTitle(model.getTitle())
            .setAuthor(model.getAuthor())
            .setPrice(model.getPrice())
            .setStock(model.getStock())
            .setYear(model.getYear())
            .setCategoryId(model.getCategoryId())
            .setImageBase64(model.getImageBase64())
            .setCategoryDetail(null)
            .setOrderItemList(null);
        return dto;
    }

    public static BooksDto toDetailDto(Books model){
        BooksDto dto = toBaseDto(model);
        dto.setCategoryDetail(null)
            .setOrderItemList(null);
        return dto;
    }
}
