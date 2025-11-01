package com.github.donnyk22.project.models.mappers;

import com.github.donnyk22.project.models.dtos.CategoriesDto;
import com.github.donnyk22.project.models.entities.Categories;
import java.util.stream.Collectors;

public class CategoriesMapper {
    public static CategoriesDto toBaseDto(Categories model){
        CategoriesDto dto = new CategoriesDto()
            .setId(model.getId())
            .setName(model.getName());
        return dto;
    }

    public static CategoriesDto toDetailDto(Categories model){
        CategoriesDto dto = toBaseDto(model);
        dto.setBookList(model.getBooks()
            .stream()
            .map(BooksMapper::toBaseDto)
            .collect(Collectors.toList()));
        return dto;
    }
}
