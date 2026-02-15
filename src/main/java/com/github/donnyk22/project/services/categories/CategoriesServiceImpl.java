package com.github.donnyk22.project.services.categories;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.CategoriesDto;
import com.github.donnyk22.project.models.entities.Categories;
import com.github.donnyk22.project.models.mappers.CategoriesMapper;
import com.github.donnyk22.project.repositories.CategoriesRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Override
    public CategoriesDto create(String category) {
        Categories newCategory = new Categories()
            .setName(category);
        categoriesRepository.save(newCategory);
        return CategoriesMapper.toBaseDto(newCategory);
    }

    @Override
    @Cacheable(value = "category", key = "#id")
    public CategoriesDto findOne(Integer id) {
        Categories category = categoriesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        return CategoriesMapper.toDetailDto(category);
    }

    @Override
    public List<CategoriesDto> findAll() {
        Iterable<Categories> categories = categoriesRepository.findAll();
        return StreamSupport
                .stream(categories.spliterator(), false)
                .map(CategoriesMapper::toBaseDto)
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(value = "category", key = "#id")
    public CategoriesDto update(Integer id, String name) {
        if (id == null || StringUtils.isBlank(name)){
            throw new BadRequestException("Id and Category name is required");
        }
        Categories category = categoriesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        category.setName(name);
        categoriesRepository.save(category);
        return CategoriesMapper.toBaseDto(category);
    }

    @Override
    @CacheEvict(value = "category", key = "#id")
    public CategoriesDto delete(Integer id) {
        Categories category = categoriesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        categoriesRepository.deleteById(id);
        return CategoriesMapper.toBaseDto(category);
    }
    
}
