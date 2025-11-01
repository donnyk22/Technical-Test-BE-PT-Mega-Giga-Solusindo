package com.github.donnyk22.project.services.categories;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.models.dtos.CategoriesDto;
import com.github.donnyk22.project.models.entities.Categories;
import com.github.donnyk22.project.models.mappers.CategoriesMapper;
import com.github.donnyk22.project.repositories.CategoriesRepository;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);

    @Autowired
    CategoriesRepository categoriesRepository;

    @Override
    public CategoriesDto create(String category) throws Exception {
        if(category.equals("")){
            logger.error("Category is required");
            throw new Exception("Category is required");
        }
        Categories newCategory = new Categories()
            .setName(category);
        categoriesRepository.save(newCategory);
        logger.info("Category is created successfully");
        return CategoriesMapper.toBaseDto(newCategory);
    }

    @Override
    public CategoriesDto findOne(Integer id) throws Exception {
        if(id == null){
            logger.error("Id is required");
            throw new Exception("Id is required");
        }
        Categories category = categoriesRepository.findById(id).orElse(null);
        if (category == null){
            logger.error("Category not found: " + id);
            throw new Exception("Category not found");
        }
        logger.info("Category found: " + id);
        return CategoriesMapper.toDetailDto(category);
    }

    @Override
    public List<CategoriesDto> findAll() throws Exception {
        Iterable<Categories> categories = categoriesRepository.findAll();
        return StreamSupport
                .stream(categories.spliterator(), false)
                .map(CategoriesMapper::toBaseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriesDto update(Integer id, String name) throws Exception {
        Categories category = categoriesRepository.findById(id).orElse(null);
        if (category == null){
            logger.error("Category not found: " + id);
            throw new Exception("Category not found");
        }
        category.setName(name);
        categoriesRepository.save(category);
        logger.info("Category updated successfuly: " + id);
        return CategoriesMapper.toBaseDto(category);
    }

    @Override
    public CategoriesDto delete(Integer id) throws Exception {
        if(id == null){
            logger.error("Id is required");
            throw new Exception("Id is required");
        }
        Categories category = categoriesRepository.findById(id).orElse(null);
        if (category == null){
            logger.error("Category not found: " + id);
            throw new Exception("Category not found");
        }
        categoriesRepository.deleteById(id);
        logger.info("Category deleted successfuly: " + id);
        return CategoriesMapper.toBaseDto(category);
    }
    
}
