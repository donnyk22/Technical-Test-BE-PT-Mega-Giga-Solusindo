package com.github.donnyk22.project.services.categories;

import java.util.List;

import com.github.donnyk22.project.models.dtos.CategoriesDto;

public interface CategoriesService {
    CategoriesDto create(String category);
    CategoriesDto findOne(Integer id);
    List<CategoriesDto> findAll();
    CategoriesDto update(Integer id, String name);
    CategoriesDto delete(Integer id);
}
