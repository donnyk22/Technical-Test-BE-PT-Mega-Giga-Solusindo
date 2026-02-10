package com.github.donnyk22.project.models.dtos;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CategoriesDto {
    private Integer id;
    private String name;
    private List<BooksDto> bookList;
}
