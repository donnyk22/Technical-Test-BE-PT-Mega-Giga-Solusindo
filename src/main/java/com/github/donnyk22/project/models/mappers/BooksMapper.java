package com.github.donnyk22.project.models.mappers;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.forms.BookAddForm;

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
            .setImageBase64(model.getImageBase64());
        return dto;
    }

    public static BooksDto toDetailDto(Books model){
        BooksDto dto = toBaseDto(model);
        dto.setCategoryDetail(CategoriesMapper.toBaseDto(model.getCategory()))
            .setOrderItemList(model.getOrderItems()
            .stream()
            .map(OrderItemsMapper::toBaseDto)
            .toList());
        return dto;
    }

    public static Books toEntity(BookAddForm form, String imageBase64){
        Books model = new Books().setTitle(form.getTitle())
            .setAuthor(form.getAuthor())
            .setPrice(form.getPrice())
            .setStock(form.getStock())
            .setYear(form.getYear())
            .setImageBase64(imageBase64);
        model.setCategoryId(form.getCategoryId());
        return model;
    }

    public static Books toEntityWithId(Integer id, BookAddForm form, String imageBase64){
        Books model = toEntity(form, imageBase64);
        model.setId(id);
        return model;
    }
}
