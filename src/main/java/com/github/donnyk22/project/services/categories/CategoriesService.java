package com.github.donnyk22.project.services.categories;

import java.util.List;

import com.github.donnyk22.project.models.dtos.CategoriesDto;

public interface CategoriesService {
    CategoriesDto create(String category) throws Exception;
    CategoriesDto findOne(Integer id) throws Exception;
    List<CategoriesDto> findAll() throws Exception;
    CategoriesDto update(Integer id, String name) throws Exception;
    CategoriesDto delete(Integer id) throws Exception;
}
