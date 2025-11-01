package com.github.donnyk22.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.github.donnyk22.project.models.entities.Categories;

@Repository
public interface CategoriesRepository extends CrudRepository<Categories, Integer> {
}
